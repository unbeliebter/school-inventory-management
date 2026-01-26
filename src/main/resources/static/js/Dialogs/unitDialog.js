function fillDialog(tableItem) {
    let itemId = document.getElementById("id-input");
    let itemNameField = document.getElementById("name-input");

    itemId.value = tableItem.id;
    itemNameField.value  = tableItem.name;

}