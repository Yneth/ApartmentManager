package ua.abond.lab4.web.dto;

public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
    private String newPasswordCopy;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordCopy() {
        return newPasswordCopy;
    }

    public void setNewPasswordCopy(String newPasswordCopy) {
        this.newPasswordCopy = newPasswordCopy;
    }
}
