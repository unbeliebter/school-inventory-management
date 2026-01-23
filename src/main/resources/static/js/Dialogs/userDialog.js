function fillDialog(user) {
    let itemId = document.getElementById("id-input");
    let userNameField = document.getElementById("username-input");
    let firstNameField = document.getElementById("firstname-input");
    let lastNameField = document.getElementById("lastname-input");
    let eMailField = document.getElementById("email-input");
    let userTypeField = document.getElementById("userType-input");

    itemId.value = user.id;
    userNameField.value  = user.username;
    firstNameField.value = user.firstname;
    lastNameField.value  = user.lastname;
    eMailField.value  = user.email;
    userTypeField.value  = user.userType;
}