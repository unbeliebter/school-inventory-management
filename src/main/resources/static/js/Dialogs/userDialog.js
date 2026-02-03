function fillDialog(tableItem) {
    let itemId = document.getElementById("id-input");
    let userNameField = document.getElementById("username-input");
    let firstNameField = document.getElementById("firstname-input");
    let lastNameField = document.getElementById("lastname-input");
    let eMailField = document.getElementById("email-input");
    let userTypeField = document.getElementById("userType-input");

    itemId.value = tableItem.id;
    userNameField.value  = tableItem.username;
    firstNameField.value = tableItem.firstname;
    lastNameField.value  = tableItem.lastname;
    eMailField.value  = tableItem.email;
    userTypeField.value  = tableItem.role.id;
}