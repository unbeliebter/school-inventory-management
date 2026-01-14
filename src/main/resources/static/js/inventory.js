const openDialog = document.getElementById("openEquipmentDialogButton");
const closeDialogButton = document.getElementById("add-equipment-dialog-cancel-button");
const dialog = document.getElementById("addEquipmentDialog");


console.log("WOW MUCH CONSOLE LOG")

// Update button opens a modal dialog
openDialog.addEventListener("click", () => {
    dialog.showModal();
});

closeDialogButton.addEventListener("click", () => {
    dialog.close();
});

function editEquipmentEntry(equipmentId) {
    console.log(equipmentId);
    console.log(equipment);

    let editedEquipment;
    for (let i = 0; i < equipment.length; i++) {
        if (equipmentId === equipment[i].id) {
            editedEquipment = equipment[i];
            break;
        }
    }
    console.log("FOUND: " + editedEquipment.id);

    fillDialog(editedEquipment);

    console.log(editedEquipment.organizationalUnit.name);

    openDialog.click();
}

function fillDialog(equipment) {
    let equipmentNameField = document.getElementById("equipmentName-input");
    let inventoryNumberField = document.getElementById("inventoryNumber-input");
    let equipmentStateField = document.getElementById("equipmentState-input");
    let organizationalUnitField = document.getElementById("organizationalUnit-input");
    let organizationalGroupField = document.getElementById("organizationalGroup-input");
    let subjectField = document.getElementById("subject-input");
    let positionField = document.getElementById("position-input");
    let responsibleUserField = document.getElementById("responsibleUser-input");

    equipmentNameField.value  = equipment.equipmentName;
    inventoryNumberField.value = equipment.inventoryNumber;
    equipmentStateField.value = equipment.equipmentState;
    organizationalUnitField.value = equipment.organizationalUnit.id;
    organizationalGroupField.value = equipment.organizationalGroup.id;
    subjectField.value = equipment.subject.id;
    positionField.value = equipment.position.id;
    responsibleUserField.value = equipment.responsibleUser.id;
    //inventoryNumber
    //equipmentState
    //organizationalUnit.name
    //organizationalGroup.name
    //subject.Stuff
    //position. ${position.school} + ' ' + ${position.room}"
    //responsibleUser. ${user.firstname} + ' ' + ${user.lastname}"
}