package mybeans.component1;

import java.util.EventListener;

public interface DataSheetChangeListener extends EventListener {
    void dataChanged(DataSheetChangeEvent e);
}