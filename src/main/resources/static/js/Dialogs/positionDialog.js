function fillDialog(tableItem) {
    let itemId = document.getElementById("id-input");
    let schoolField = document.getElementById("school-input");
    let roomField = document.getElementById("room-input");
    let descriptionField = document.getElementById("description-input");

    itemId.value = tableItem.id;
    schoolField.value  = tableItem.school;
    roomField.value  = tableItem.room;
    descriptionField.value = tableItem.description;

}