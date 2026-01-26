function fillDialog(position) {
    let itemId = document.getElementById("id-input");
    let schoolField = document.getElementById("school-input");
    let roomField = document.getElementById("room-input");
    let descriptionField = document.getElementById("description-input");

    itemId.value = position.id;
    schoolField.value  = position.school;
    roomField.value  = position.room;
    descriptionField.value = position.description;

}