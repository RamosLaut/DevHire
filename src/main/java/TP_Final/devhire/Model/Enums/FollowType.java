package TP_Final.devhire.Model.Enums;

public enum FollowType {
    DEVELOPER_TO_DEVELOPER, DEVELOPER_TO_COMPANY, COMPANY_TO_DEVELOPER, COMPANY_TO_COMPANY;

    public static FollowType from(EntityType follower, EntityType followed) {
        return switch (follower) {
            case DEVELOPER -> switch (followed) {
                case DEVELOPER -> DEVELOPER_TO_DEVELOPER;
                case COMPANY -> DEVELOPER_TO_COMPANY;
            };
            case COMPANY -> switch (followed) {
                case DEVELOPER -> COMPANY_TO_DEVELOPER;
                case COMPANY -> COMPANY_TO_COMPANY;
            };
        };
    }
}
