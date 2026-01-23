function fillDialog(equipment) {
    let equipmentId = document.getElementById("id-input");
    let equipmentNameField = document.getElementById("itemName-input");
    let inventoryNumberField = document.getElementById("inventoryNumber-input");
    let equipmentStateField = document.getElementById("equipmentState-input");
    let organizationalUnitField = document.getElementById("organizationalUnit-input");
    let organizationalGroupField = document.getElementById("organizationalGroup-input");
    let subjectField = document.getElementById("subject-input");
    let positionField = document.getElementById("position-input");
    let responsibleUserField = document.getElementById("responsibleUser-input");

    equipmentId.value = equipment.id;
    equipmentNameField.value  = equipment.equipmentName;
    inventoryNumberField.value = equipment.inventoryNumber;
    equipmentStateField.value = equipment.equipmentState;
    organizationalUnitField.value = equipment.organizationalUnit.id;
    organizationalGroupField.value = equipment.organizationalGroup.id;
    subjectField.value = equipment.subject.id;
    positionField.value = equipment.position.id;
    responsibleUserField.value = equipment.responsibleUser.id;
}