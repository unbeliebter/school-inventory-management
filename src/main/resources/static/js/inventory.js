const openDialog = document.getElementById("openEquipmentDialogButton");
const closeDialogButton = document.getElementById("item-dialog-cancel-button");
const dialog = document.getElementById("itemDialog");

console.log("WOW MUCH CONSOLE LOG")

// Update button opens a modal dialog
openDialog.addEventListener("click", () => {
    document.getElementById("id-input").value = "new";
    dialog.showModal();
});

closeDialogButton.addEventListener("click", () => {
    dialog.close();
});

function editTableItem(itemId) {
    let itemToEdit;
    for (let i = 0; i < tableItems.length; i++) {
        if (itemId === tableItems[i].id) {
            itemToEdit = tableItems[i];
            break;
        }
    }

    fillDialog(itemToEdit);

    dialog.showModal();
}

function toggleMenu() {
    let navSidebar = document.getElementById("nav-sidebar");
    let isOpen = navSidebar.getAttribute("open");
    if (isOpen === "true") {
        navSidebar.setAttribute("open", "false");
    } else if (isOpen) {
        navSidebar.setAttribute("open", "true");
    }
}