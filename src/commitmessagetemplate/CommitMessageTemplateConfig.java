package commitmessagetemplate;

import java.util.LinkedList;
import java.util.List;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;


@State(
        name = "CommitMessageTemplateConfig",
        storages = {
                @Storage("CommitMessageTemplateConfig.xml")}
)
public class CommitMessageTemplateConfig implements PersistentStateComponent<CommitMessageTemplateConfig> {

    private List<CommitMessageTemplate> commitTemplates = new LinkedList<>();

    public CommitMessageTemplateConfig() {
        // DO NOTHING
    }

    @Nullable
    @Override
    public CommitMessageTemplateConfig getState() {
        return this;
    }

    @Override
    public void loadState(CommitMessageTemplateConfig commitMessageTemplateConfig) {
        XmlSerializerUtil.copyBean(commitMessageTemplateConfig, this);
    }

    @Nullable
    static CommitMessageTemplateConfig getInstance(Project project) {
        return ServiceManager.getService(project, CommitMessageTemplateConfig.class);
    }

    public void addCommitTemplate(CommitMessageTemplate commitMessageTemplate) {
        this.commitTemplates.add(commitMessageTemplate);
    }

    public void removeCommitTemplate(CommitMessageTemplate commitMessageTemplate) {
        this.commitTemplates.remove(commitMessageTemplate);
    }

    public List<CommitMessageTemplate> getTemplates() {
        return commitTemplates;
    }
}
