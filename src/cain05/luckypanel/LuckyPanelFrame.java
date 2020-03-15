package cain05.luckypanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LuckyPanelFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static final int playAreaWidth 	= 5;
	private static final int playAreaHeight = 4;
	
	private ImageIcon[] images = new ImageIcon[46];
	private JLabel[][] playLabels = new JLabel[playAreaWidth][playAreaHeight];
	private ImageSize	imageSize;

	/**
	 * Constructor
	 */
	public LuckyPanelFrame(ImageSize size)
	{
		this.imageSize = size;
		
		setTitle("Lucky Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);
		setResizable(false);
		
		loadImages();
		setupUI();
		pack();
		setVisible(true);
	}
	
	/**
	 * Sets up the user interface
	 */
	private void setupUI()
	{
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		
		this.add(setupSidePanel(0, 22), c);
		
		c.gridx = 1;
		c.gridy = 0;
		
		this.add(setupPlayArea(), c);
		
		c.gridx = 2;
		c.gridy = 0;
		
		this.add(setupSidePanel(23, 45));
		
		c.gridx = 0;
		c.gridy = 1;
		
		this.add(setupAboutButton(), c);
		
		c.gridx = 1;
		c.gridy = 1;
		
		this.add(setupResetButton(), c);
		
		c.gridx = 2;
		c.gridy = 1;
		
		this.add(setupImageSizeComboBox(), c);		
	}
	
	/**
	 * Sets up the side panels of cards to choose from
	 * 
	 * @param first the first card to display
	 * @param last the last card to display
	 * 
	 * @return the side panel
	 */
	private JPanel setupSidePanel(int first, int last)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5,5));
		
		for(int i=first; i<=last; i++)
		{
			JLabel label = new JLabel();
			label.setIcon(this.images[i]);
			label.setTransferHandler(new ImageSelection()
			{
				private static final long serialVersionUID = 1L;

				public boolean importData(JComponent comp, Transferable t) 
				 {
					 return false;
				 }
			});
		    label.addMouseListener(new DragAndDropMouseAdapter());
			panel.add(label);
		}
		
		return panel;
	}
	
	/**
	 * Sets up the reset button
	 * 
	 * @return the reset button
	 */
	private JButton setupResetButton()
	{
		JButton button = new JButton("Reset");
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				resetPlayArea();
			}
		});
		return button;
	}
	
	/**
	 * Sets up the about button
	 * 
	 * @return about
	 */
	private JButton setupAboutButton()
	{
		return new JButton("About");
	}
	
	/**
	 * Sets up the image size combo box
	 * 
	 * @return image size combo box
	 */
	private JPanel setupImageSizeComboBox()
	{
		JComboBox	combo = new JComboBox();
		combo.addItem(ImageSize.values()[0]);
		combo.addItem(ImageSize.values()[1]);
		combo.addItem(ImageSize.values()[2]);
		combo.setSelectedItem(this.imageSize);
	
		combo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JComboBox combo = (JComboBox)e.getSource();
				imageSize = (ImageSize)combo.getSelectedItem();
				loadImages();
				updatePlayAreaImages();
				pack();
				
				LuckyPanel.setDefaultImageSize(imageSize);
			}
		});
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("Image Size: "));
		panel.add(combo);
		
		return panel;
	}
	
	/**
	 * Sets up the play area
	 * 
	 * @return the play area
	 */
	private JPanel setupPlayArea()
	{
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(4,5);
		layout.setHgap(0);
		layout.setVgap(0);
		panel.setLayout(layout);
		
		for(int i=0; i<playAreaWidth; i++)
		{
			for(int j=0; j<playAreaHeight; j++)
			{
				JLabel label = new JLabel();
				label.setTransferHandler(new ImageSelection());
			    label.addMouseListener(new DragAndDropMouseAdapter());
				this.playLabels[i][j] = label;
				panel.add(label);
			}
		}
		resetPlayArea();
		
		return panel;
	}
	
    /** 
     * Returns an ImageIcon, or null if the path was invalid. 
     */
    private ImageIcon createImageIcon(String path) 
    {
        ImageIcon imageIcon = null;
    	URL imgURL = getClass().getResource(path);
        if (imgURL != null) 
        {
        	imageIcon = new ImageIcon(imgURL);
        } 
        else 
        {
            System.err.println("Couldn't find file: " + path);
        }
        return imageIcon;
    }
    
    /**
     * loads the images
     */
    private void loadImages()
    {
    	for (int i=0; i<this.images.length; i++)
    	{
    		ImageIcon newImage = createImageIcon(String.format("/images/%s/LP%02d.png", this.imageSize.toString(), i));
    		newImage.setDescription(String.valueOf(i));
    		if (this.images[i] == null)
    		{
    			images[i] = newImage;
    		}
    		else
    		{
    			this.images[i].setImage(newImage.getImage());
    		}
    	}
    }
    
    private void updatePlayAreaImages()
    {
		for(int i=0; i<playAreaWidth; i++)
		{
			for(int j=0; j<playAreaHeight; j++)
			{
				ImageIcon current = (ImageIcon)this.playLabels[i][j].getIcon();
				this.playLabels[i][j].setIcon(this.images[Integer.valueOf(current.getDescription())]);
			}
		}
    }
    
    /**
     * Sets the play area panels back to the default image
     */
    private void resetPlayArea()
    {
		for(int i=0; i<playAreaWidth; i++)
		{
			for(int j=0; j<playAreaHeight; j++)
			{
				this.playLabels[i][j].setIcon(this.images[0]);
			}
		}
    }
}
