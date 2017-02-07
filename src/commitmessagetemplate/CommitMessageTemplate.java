/*
 * Copyright (c) 2016 Bull/Atos.
 * All rights reserved.
 */
package commitmessagetemplate;

/**
 * [Enter type description here].
 *
 * @author Bull/Atos
 */
public class CommitMessageTemplate {

    private String templateName;

    private String commitMessage;

    public CommitMessageTemplate(String templateName, String commitMessage) {
        this.templateName = templateName;
        this.commitMessage = commitMessage;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public String toString() {
        return templateName;
    }
}
