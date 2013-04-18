package code;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Pedro
 */
public class TableDialog extends JDialog{
    
    private final int DEF_WIDTH = 800;
    private final int DEF_HEIGHT = 400;
    private final Dimension DEFAULT_DIMENSION = new Dimension(DEF_WIDTH, DEF_HEIGHT);
    private TableModel tModel;
    
    public TableDialog(JFrame owner, String title, String[][] tabledata, String[] tablehead){
        super(owner);
        setModal(true);
        Container c = getContentPane();
        setSize(DEFAULT_DIMENSION);     
        setTitle(title);
        c.setLayout(new BorderLayout());
        
        tModel = new TableModel(tabledata, tablehead);
        JTable t = new JTable(tModel){
            /**
             * Renderiza as linhas da tabela com cores diferentes intercaladas
             * para facilitar a leitura.
             * Snippet retirado de:
             * http://blog.marcnuri.com/blog/.../2007/03/15/JTable-Row-Alternate-Row-Background
             */
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                Component returnComp = super.prepareRenderer(renderer, row, column);
                Color alternateColor = new Color(220,220,220);
                Color whiteColor = Color.WHITE;
                if (!returnComp.getBackground().equals(getSelectionBackground())){
                    Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
                    returnComp .setBackground(bg);
                    bg = null;
                }
                return returnComp;
        }};
        
        JButton bExit = new JButton("Sair");
        bExit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }            
        });
        
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p2.add(bExit);
        
        JScrollPane sp = new JScrollPane(t);
        c.add(sp, BorderLayout.CENTER);
        c.add(p2, BorderLayout.SOUTH);
        
    }
    
    public void update(){
        tModel.fireTableDataChanged();
    }
    
    /*
     * This class provides implementation of methods used by and for the table
     * Also is set table to not be directly editable
     */    
    private class TableModel extends AbstractTableModel{
        private String[] header = null;
        private Object[][] data = null;
        
        public TableModel(Object[][] data, String[] header){
            this.header = header;
            this.data = data;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return header.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }
        
        @Override
        public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
            return false;
        }
        
    }

}
