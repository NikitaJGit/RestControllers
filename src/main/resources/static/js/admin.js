$(document).ready(function(){

    $('#newUserLink').click(function(){
        clearNewUser();
    });

    $('#create_user').click(function(){
        addUser();
    });

    $('#clear_user').click(function(){
        clearNewUser();
    });

    refreshUsersTable()
});

 function clearNewUser(){
    $('#loginN').val("");
    $('#passwordN').val("");
    $('#nameN').val("");
    $('#surnameN').val("");
    $('#ageN').val("");
    $('#rolesListN').val([]);
    $('#rolesListN option:selected').attr('selected',false);
 }

 function addUser() {
    let arrRolesList = [];
    $('#securityRolesListN option:selected').each(function () {
        arrRolesList.push(JSON.parse($(this).val()));
    });
    let user = {
        username: $('#loginN').val(),
        password: $('#passwordN').val(),
        name:$('#nameN').val(),
        surname:$('#surnameN').val(),
        age: $('#ageN').val(),
        roleList: $('#roleListN').val().split(","),
        roles: $('#rolesN').val(),
        rolesList: arrRolesList

    };
    $.ajax({
        url: 'http://localhost:8080/api/admin/new',
        type: 'post',
        data: JSON.stringify(user),
        headers: {
            'x-auth-token': localStorage.accessToken,
            "Content-Type": "application/json"
        },
        dataType: 'json',
        success: function(){
            refreshUsersTable();
            $('#userTablePage').tab('show');
        },
        error: (data) => showError(data)
    })
 }

 function openDeleteUserModal(userId) {
     $.get(`http://localhost:8080/api/admin/${userId}`)
        .done((user) => {
            $('#idD').val(user.id);
            $('#loginD').val(user.username);
            $('#nameD').val(user.name)
            $('#surnameD').val(user.surname);
            $('#ageD').val(user.age);
            $('#rolesListD option:selected').attr('selected',false);
            console.log(user)
            for(let i = 0; i < user.roleList.length; i++){
                $('#rolesListD option:contains(user.roleList[i])').attr('selected', true);
            }

            $('#modalDelete').modal('show');


            $('#delete_button').off("click");
             $('#delete_button').on("click", function () {
                 deleteUser(userId);
             })
        });
 }

 function openEditUserModal(userId) {
    console.log("openEditUserModal " + userId);

    $.ajax({
        url: `http://localhost:8080/api/admin/${userId}`,
        type: 'get',
        headers: {
            'x-auth-token': localStorage.accessToken,
        },
        success: (user) => {
            sendEditRequest(user);
        },
        error: (data) => showError(data)
    })
 }

 function sendEditRequest(user) {
    const userId = user.id;

    console.log("sendEditRequest " + userId);

    $('#loginE').val(user.login);
    $('#passwordE').val(user.password);
    $('#idE').val(user.id);
     $('#nameE').val(user.name);
     $('#surnameE').val(user.surname);
    $('#ageE').val(user.age);
    $('#rolesE').val(user.roles);
    $('#roleListE').val(user.roleList);

    $('#roleListE option:selected').attr('selected',false);

    for(let i = 0; i < user.roleList.length; i++){
        $('#rolesListE option:contains(user.roleList[i].role)').attr('selected', true);
     }

    $('#modalEdit').modal('show');

    $('#edit_button').off("click");

    $('#edit_button').click(() => {
        console.log("edit_button click " + userId);
        let arrRolesList = [];
        $('#rolesListE option:selected').each(function(){
            arrRolesList.push(JSON.parse($(this).val()));
        });

        let user = {
            id: $('#idE').val(),
            login: $('#loginE').val(),
            age: $('#ageE').val(),
            email: $('#emailE').val(),
            password: $('#passwordE').val(),
            roleList: $('#roleListE').val().split(","),
            roles: $('#rolesE').val(),
            rolesList: arrRolesList
        };
        $.ajax({
            url: `http://localhost:8080/api/admin/${userId}`,
            type: 'patch',
            data: JSON.stringify(user),
            headers: {
                'x-auth-token': localStorage.accessToken,
                "Content-Type": "application/json"
            },
            dataType: 'json',
            success: refreshUsersTable,
            error: (data) => showError(data)
        })
    });

}

 function refreshUsersTable() {
    $.get('http://localhost:8080/api/users/me')
        .done(() => {
            fillInUsersTable(true);
        })
 }
 function fillInUsersTable() {
    $.get('http://localhost:8080/api/users?t=' + (((new Date()).getTime() * 10000) + 621355968000000000))
        .done((usersList) => {
            $('#modalEdit').modal('hide');
            $('#modalDelete').modal('hide');
            $(document).ready(() => $("#userTablePage").click());
            $(document).ready(() => $("#usersTableBody").empty());
            $(document).ready(() => {
                for (let i = 0; i < usersList.length; i++) {
                    const user = usersList[i];
                    console.log(user)
                    $("#usersTableBody")
                        .append($('<tr>')
                                .append($('<td>').text(user.id))
                                .append($('<td>').text(user.name))
                                .append($('<td>').text(user.surname))
                                .append($('<td>').text(user.login))
                                .append($('<td>').text(user.age))
                                .append($('<td>').text(user.roleList))
                                .append($('<td>').html("<input onclick=\"openEditUserModal(" + user.id + ")\" type=\"button\" class=\"btn btn-primary\"\n" +
                                    "value=\"Edit\">"))
                                .append($('<td>').html("<input onclick=\"openDeleteUserModal(" + user.id + ")\" type=\"button\" class=\"btn btn-danger\"\n" +
                                    "value=\"Delete\">"))
                        );

                }
            });
        });
 }


 function deleteUser(userId) {
    $.ajax({
        url: `http://localhost:8080/api/admin/${userId}`,
        type: 'DELETE',
        headers: {
            'x-auth-token': localStorage.accessToken
        },
        success: refreshUsersTable,
        error: (data) => showError(data)
    })
 }

 function showError(message) {
    alert(message.responseText);
 }