package commitmessagetemplate;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.intellij.openapi.project.Project;


/**
 * Created by matan.goren on 23-Sep-16.
 */
public class CommitMessageTemplateConfigurableGUI {

    private JPanel rootPanel;
    private JButton addButton;
    private JTextField templateName;
    private JTextArea commitMessage;
    private JComboBox<CommitMessageTemplate> templateList;
    private JButton removeButton;

    private CommitMessageTemplateConfig config;

    private void initListeners() {
        this.addButton.addActionListener(e -> onAddTemplate());
        this.removeButton.addActionListener(e -> onRemoveTemplate());
    }

    private void onAddTemplate() {
        if(this.commitMessage.getText().isEmpty() || this.templateName.getText().isEmpty()) {
            return;
        }
        CommitMessageTemplate template = new CommitMessageTemplate(this.templateName.getText(),
                this.commitMessage.getText());
        config.addCommitTemplate(template);
        this.templateList.addItem(template);
    }

    private void onRemoveTemplate() {
        CommitMessageTemplate template = (CommitMessageTemplate) templateList.getSelectedItem();
        if (template == null) {
            return;
        }
        config.removeCommitTemplate(template);
        this.templateList.removeItem(template);
    }

    void createUI(Project project) {
        config = CommitMessageTemplateConfig.getInstance(project);
        if (config != null) {
            initListeners();
            initList();
        }
    }

    private void initList() {
        List<CommitMessageTemplate> templateList = config.getTemplates();
        templateList.stream().forEach(t -> this.templateList.addItem(t));
    }

    JPanel getRootPanel() {
        return rootPanel;
    }
}
