package com.example.chaterserver.enums.typeenum;

public enum FileTypeEnum {
    CHAT_PICTURE(0),
    HEAD_PIC(1),
    STATIC_RESOURCE(2);

    int typeNum;
    FileTypeEnum(int typeNum){
        this.typeNum = typeNum;
    }

    public int getTypeNum() {
        return typeNum;
    }

    public static FileTypeEnum getFileTypeEnumByTypeNum(int i){
        for (FileTypeEnum typeEnum:FileTypeEnum.values()
        ) {
            if(typeEnum.getTypeNum() == i){
                return typeEnum;
            }
        }

        return null;
    }

}
