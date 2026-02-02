function fillDialog(tableItem) {
    let equipmentId = document.getElementById("id-input");
    let equipmentNameField = document.getElementById("itemName-input");
    let inventoryNumberField = document.getElementById("inventoryNumber-input");
    let equipmentStateField = document.getElementById("equipmentState-input");
    let organizationalUnitField = document.getElementById("organizationalUnit-input");
    let organizationalGroupField = document.getElementById("organizationalGroup-input");
    let subjectField = document.getElementById("subject-input");
    let positionField = document.getElementById("position-input");
    let responsibleUserField = document.getElementById("responsibleUser-input");

    equipmentId.value = tableItem.id;
    equipmentNameField.value  = tableItem.equipmentName;
    inventoryNumberField.value = tableItem.inventoryNumber;
    equipmentStateField.value = tableItem.equipmentState;
    organizationalUnitField.value = tableItem.organizationalUnit.id;
    organizationalGroupField.value = tableItem.organizationalGroup.id;
    subjectField.value = tableItem.subject.id;
    positionField.value = tableItem.position == null ? "" : tableItem.position.id;
    responsibleUserField.value = tableItem.responsibleUser == null ?  "" : tableItem.responsibleUser.id;
}