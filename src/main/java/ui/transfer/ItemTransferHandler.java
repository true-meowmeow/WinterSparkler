package ui.transfer;


import javax.swing.*;
import java.awt.datatransfer.*;
import java.io.Serial;

public final class ItemTransferHandler extends TransferHandler {
    @Serial private static final long serialVersionUID = 1L;

    public static final DataFlavor PAYLOAD_FLAVOR;

    static {
        try {
            PAYLOAD_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType
                    + ";class=" + ItemMovePayload.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private final ItemTransferModel model;
    private final ItemFolder targetItems;
    private final JList<Item> sourceList;

    public ItemTransferHandler(ItemTransferModel model, ItemFolder targetItems, JList<Item> sourceList) {
        this.model = model;
        this.targetItems = targetItems;
        this.sourceList = sourceList;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        Item selected = sourceList.getSelectedValue();
        if (selected == null) return null;

        ItemMovePayload payload = new ItemMovePayload(selected);

        return new Transferable() {
            @Override public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{ PAYLOAD_FLAVOR };
            }

            @Override public boolean isDataFlavorSupported(DataFlavor flavor) {
                return PAYLOAD_FLAVOR.equals(flavor);
            }

            @Override public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
                if (!isDataFlavorSupported(flavor)) throw new UnsupportedFlavorException(flavor);
                return payload;
            }
        };
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return support.isDrop() && support.isDataFlavorSupported(PAYLOAD_FLAVOR);
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) return false;

        try {
            ItemMovePayload payload = (ItemMovePayload) support.getTransferable().getTransferData(PAYLOAD_FLAVOR);
            model.moveItem(payload.item, targetItems);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
