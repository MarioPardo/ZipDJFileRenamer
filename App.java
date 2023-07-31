import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class App extends JFrame 
{

    private JTextField folderPathField;
    String folderPathString = "";
    boolean mustSelectFolder = false;
    JButton browseButton;

    public App() 
    {
        setTitle("Rename Files");
        setSize(400, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        folderPathField = new JTextField();
        folderPathField.setEditable(false);
       

        browseButton = new JButton("Select Folder");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                selectFolder();
            }
        });

        

        JButton renameButton = new JButton("Rename!");
        renameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                 if(folderPathString == "")
                {
                    browseButton.setOpaque(true);
                    browseButton.setBackground(new Color(255, 20, 20, 190));
                    shakeWindow(3, 30);
                    mustSelectFolder = true;
                   
                    return;
                }
                renameFiles();
            }
        });

        add(folderPathField, BorderLayout.NORTH);
        add(renameButton, BorderLayout.CENTER);
        add(browseButton, BorderLayout.SOUTH);
    }

    private void selectFolder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = fileChooser.getSelectedFile();
            folderPathString = selectedFolder.getAbsolutePath();
            folderPathField.setText("Folder:" + selectedFolder.getAbsolutePath());
            if(mustSelectFolder)
            {
                browseButton.setOpaque(true);
                browseButton.setBackground(null);
            }

        }
    }

    private void renameFiles()
    {
        File folder = new File(folderPathString);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++)
        {
        if (listOfFiles[i].isFile()) 
        {
         String currFileName = listOfFiles[i].getName();
         System.out.println("File " + listOfFiles[i].getName());
            int lastDash = currFileName.lastIndexOf("-");



    if(Character.isDigit(currFileName.charAt(lastDash + 1))) //then we have to rename
    {
        String newName = currFileName.substring(0, lastDash);
        if(newName.charAt(newName.length()-1) == ' ' || newName.charAt(newName.length()-1) == '_')
            newName = newName.substring(0, newName.length()-2);

        //now actually rename
        File currFile = new File(folderPathString + "/" + currFileName);
        File newFile = new File(folderPathString + "/" + newName+".mp3");
        

        if( currFile.renameTo(newFile))
        {
            System.out.println("Success renaming: " + currFileName);
        }else
        {
            System.out.println("Failure renaming: " + currFileName);
        }
    }





  } 
}

    }

    private void shakeWindow(int times, int pixels) {
        Point originalLocation = this.getLocation();
        Timer timer = new Timer(50, new ActionListener() {
            private int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count % 2 == 0) {
                    setLocation(originalLocation.x + pixels, originalLocation.y);
                } else {
                    setLocation(originalLocation.x - pixels, originalLocation.y);
                }
                count++;
                if (count >= times * 2) {
                    setLocation(originalLocation); // Reset to the original location
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new App().setVisible(true);
            }
        });
    }
}