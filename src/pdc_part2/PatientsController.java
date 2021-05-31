/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author libst
 */
public class PatientsController implements ActionListener{
    PatientsPanel panel;
    PatientManagementView frame;
    
    public PatientsController(PatientsPanel panel, PatientManagementView frame) {
        this.panel = panel;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == panel.getBackButton()) {
            try {
                MenuIconsPanel menuIconsPanel = new MenuIconsPanel(frame);
                frame.remove(panel);
                frame.add(menuIconsPanel);
                frame.revalidate();
            } catch (IOException ex) {
                Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else if (source == panel.getAddPatientB()) 
        {
            AddPatientsView addPanel;
            try 
            {
                addPanel = new AddPatientsView(frame, panel, frame.getWidth(), frame.getHeight());
                panel.remove(panel.getButtonsPane1());
                panel.add(addPanel, panel.getConstraints());
                frame.revalidate();
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(PatientsPanel.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(PatientsPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (source == panel.getEditPatientB()) {
            
        }
        else if (source == panel.getBrowsePatientsB()) {
            BrowsePatientsPanel browsePanel;
            try {
                browsePanel = new BrowsePatientsPanel(frame, frame.getWidth(), frame.getHeight());
                panel.remove(panel.getButtonsPane1());
                panel.add(browsePanel, panel.getConstraints());
                frame.revalidate();
            } catch (IOException ex) {
                Logger.getLogger(PatientsPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(PatientsPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
       
        }
    }
    
}
