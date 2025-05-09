package swing.objects.objects;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

// Внутренний класс для передачи данных через Drag & Drop
public class TransferableData implements Transferable {
    private final Object data;
    public static final DataFlavor OBJECT_DATA_FLAVOR =
            new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=java.lang.Object", "Local Object");

    public TransferableData(Object data) {
        this.data = data;
    }
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{OBJECT_DATA_FLAVOR};
    }
    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(OBJECT_DATA_FLAVOR);
    }
    @Override
    public Object getTransferData(DataFlavor flavor) {
        if (isDataFlavorSupported(flavor)) {
            return data;
        }
        return null;
    }
}