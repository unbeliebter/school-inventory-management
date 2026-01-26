function fillDialog(tableItem) {
    let itemId = document.getElementById("id-input");
    let itemNameField = document.getElementById("itemName-input");
    let subjectAbbreviationField = document.getElementById("subject-abbreviation-input");

    itemId.value = tableItem.id;
    itemNameField.value  = tableItem.name;
    subjectAbbreviationField.value = tableItem.abbreviation;

}