package univ.tuit.applyjobbot.ext;


import univ.tuit.applyjobbot.utils.JsonUtil;

public interface JsonSerializable {
    default String toJson() {
        return JsonUtil.toJson(this);
    }

    default String toPrettyJson() {
        return JsonUtil.toPrettyJson(this);
    }
}
