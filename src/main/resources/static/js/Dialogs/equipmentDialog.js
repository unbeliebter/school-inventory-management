

openDialogButton.addEventListener("click", () => {
    dialogAddEquipmentStateChanged();
});

function dialogAddEquipmentStateChanged() {
    let equipmentStateField = document.getElementById("equipmentState-input");
    let positionInputDiv = document.getElementById("dialog-add-item-position");
    let renterInputDiv = document.getElementById("dialog-add-item-renter");
    if (equipmentStateField.value === 'ON_LOAN') {
        positionInputDiv.style.display = 'none';
        renterInputDiv.style.display = 'flex';
    } else {
        positionInputDiv.style.display = 'flex';
        renterInputDiv.style.display = 'none';
    }
}

function fillDialog(tableItem) {
    let equipmentId = document.getElementById("id-input");
    let equipmentNameField = document.getElementById("itemName-input");
    let inventoryNumberField = document.getElementById("inventoryNumber-input");
    let equipmentStateField = document.getElementById("equipmentState-input");
    let organizationalUnitField = document.getElementById("organizationalUnit-input");
    let organizationalGroupField = document.getElementById("organizationalGroup-input");
    let subjectField = document.getElementById("subject-input");
    let positionField = document.getElementById("position-input");
    let renterField = document.getElementById("dialog-add-item-renter-input");
    let responsibleUserField = document.getElementById("responsibleUser-input");

    equipmentId.value = tableItem.id;
    equipmentNameField.value  = tableItem.equipmentName;
    inventoryNumberField.value = tableItem.inventoryNumber;
    equipmentStateField.value = tableItem.equipmentState;
    organizationalUnitField.value = tableItem.organizationalUnit.id;
    organizationalGroupField.value = tableItem.organizationalGroup.id;
    subjectField.value = tableItem.subject.id;
    if (renterField.value !== null) {
        dialogAddEquipmentStateChanged();
    }
    positionField.value = tableItem.position == null ? "" : tableItem.position.id;
    if (responsibleUserField !== null) {
        responsibleUserField.value = tableItem.responsibleUser == null ?  "" : tableItem.responsibleUser.id;
    }
}

function validateOnAdd() {
    let form = document.getElementById("newItemForm");

    let equipmentStateField = document.getElementById("equipmentState-input");
    let positionField = document.getElementById("position-input");
    let renterField = document.getElementById("dialog-add-item-renter-input");

    if (equipmentStateField === null || positionField === null) {
        return;
    }

    if (equipmentStateField.value === 'ON_LOAN') {
        positionField.value = "";
        renterField.setAttribute("required", "required");
        positionField.removeAttribute("required");
    } else {
        renterField.value = "";
        positionField.setAttribute("required", "required");
        renterField.removeAttribute("required");
    }

    if (!form.reportValidity()) {
        return;
    }
    form.submit();
}