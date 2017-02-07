package commitmessagetemplate;

/**
 * Created by matan.goren on 23-Sep-16.
 */

import javax.swing.JComponent;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class CommitMessageTemplateConfigurable implements SearchableConfigurable {

    private CommitMessageTemplateConfigurableGUI gui;

    private final Project project;

    public CommitMessageTemplateConfigurable(@NotNull Project project) {
        this.project = project;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Commit Message Template Plugin";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @NotNull
    @Override
    public String getId() {
        return "preference.CommitMessageTemplateConfigurable";
    }

    @Nullable
    @Override
    public Runnable enableSearch(String s) {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        gui = new CommitMessageTemplateConfigurableGUI();
        gui.createUI(project);
        return gui.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        // TODO
    }

    @Override
    public void reset() {
        // TODO
    }

    @Override
    public void disposeUIResources() {
        gui = null;
    }


}
