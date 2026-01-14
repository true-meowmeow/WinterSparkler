package core.ui.transfer;

public record ItemMoveEvent(Item item, ItemFolder from, ItemFolder to) {}
