/*
 * Copyright 2014 Darek Kay <darekkay@eclectide.com>
 *
 * MIT license
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package commitmessagetemplate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vcs.CommitMessageI;
import com.intellij.openapi.vcs.VcsDataKeys;
import com.intellij.openapi.vcs.ui.Refreshable;
import org.jetbrains.annotations.Nullable;

/**
 * @author darekkay
 */
public class CommitMessageTemplateAction extends AnAction implements DumbAware {

    public void actionPerformed(AnActionEvent e) {
        final CommitMessageI checkinPanel = getCheckinPanel(e);
        if (checkinPanel == null) {
            return;
        }

        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        CommitMessageTemplateConfig config = CommitMessageTemplateConfig.getInstance(project);

        JComboBox<CommitMessageTemplate> templates = new ComboBox<CommitMessageTemplate>();
        config.getTemplates().stream().forEach(t -> templates.addItem(t));
        templates.addItem(new CommitMessageTemplate("Test", "Message"));

        if (config != null) {
            SwingUtilities.invokeLater(() -> {
                TemplateChoiceDialog templateChoiceDialog = new TemplateChoiceDialog(project, templates);
                templateChoiceDialog.show();

                if (templateChoiceDialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
                    submit(checkinPanel, templates);
                }
            });
        }
    }

    public void submit(CommitMessageI panel, JComboBox<CommitMessageTemplate> templates) {
        CommitMessageTemplate template = (CommitMessageTemplate) templates.getSelectedItem();
        panel.setCommitMessage(template.getCommitMessage());

    }



    @Nullable
    private static CommitMessageI getCheckinPanel(@Nullable AnActionEvent e) {
        if (e == null) {
            return null;
        }
        Refreshable data = Refreshable.PANEL_KEY.getData(e.getDataContext());
        if (data instanceof CommitMessageI) {
            return (CommitMessageI) data;
        }
        CommitMessageI commitMessageI = VcsDataKeys.COMMIT_MESSAGE_CONTROL.getData(e.getDataContext());
        if (commitMessageI != null) {
            return commitMessageI;
        }
        return null;
    }

    private class TemplateChoiceDialog extends DialogWrapper implements ActionListener {

        private JComboBox<CommitMessageTemplate> templates;
        private JLabel commitMessage = new JLabel();

        public TemplateChoiceDialog(Project project, JComboBox<CommitMessageTemplate> templates) {
            super(project);
            this.templates = templates;
            this.templates.addActionListener(this);
            this.init();
            this.actionPerformed(null);
            this.setTitle("Template Chooser");
            this.setModal(true);
        }

        @Nullable
        @Override
        protected JComponent createCenterPanel() {
            JPanel jPanel = new JPanel();
            jPanel.add(templates);
            jPanel.add(commitMessage);
            return jPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.commitMessage.setText(((CommitMessageTemplate)templates.getSelectedItem()).getCommitMessage());
        }
    }
}
