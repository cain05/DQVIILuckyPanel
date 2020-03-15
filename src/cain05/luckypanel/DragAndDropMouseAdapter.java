package cain05.luckypanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public class DragAndDropMouseAdapter extends MouseAdapter
{
    @Override
	public void mousePressed(MouseEvent evt)
    {
      JComponent comp = (JComponent) evt.getSource();
      TransferHandler th = comp.getTransferHandler();

      th.exportAsDrag(comp, evt, TransferHandler.COPY);
    }
}
