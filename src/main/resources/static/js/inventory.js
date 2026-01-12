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