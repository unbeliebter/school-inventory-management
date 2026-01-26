function fillDialog(group) {
    let itemId = document.getElementById("id-input");
    let itemNameField = document.getElementById("name-input");

    itemId.value = group.id;
    itemNameField.value  = group.name;

}