package demo.explorer;

import javax.swing.*;
import java.awt.datatransfer.*;
import java.io.Serial;

public final class ItemTransferHandler extends TransferHandler {
    @Serial private static final long serialVersionUID = 1L;

    public static final DataFlavor PAYLOAD_FLAVOR;

    static {
        try {
            PAYLOAD_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType
                    + ";class=" + MovePayload.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private final ExplorerModel model;
    private final Folder targetFolder;
    private final JList<Item> sourceList;

    public ItemTransferHandler(ExplorerModel model, Folder targetFolder, JList<Item> sourceList) {
        this.model = model;
        this.targetFolder = targetFolder;
        this.sourceList = sourceList;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        Item selected = sourceList.getSelectedValue();
        if (selected == null) return null;

        MovePayload payload = new MovePayload(selected);

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
            MovePayload payload = (MovePayload) support.getTransferable().getTransferData(PAYLOAD_FLAVOR);
            model.moveItem(payload.item, targetFolder);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
