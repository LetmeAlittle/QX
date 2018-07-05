package com.ttt.qx.qxcall.function.login.model.entity;

/**
 * Created by wyd on 2017/7/30.
 */
public class UploadImg {
    /**
     * ErrorCode : 0
     * ExtendObj : <null>
     * Msg :
     * OK : 1
     * Obj : {"CategoryText":"","Ext":".png","FileCategory":"1","Name":"test2.pngg","Url":"http://139.219.224.246:5587/memberIcons/684708b4-040e-4591-b199-36199ed542f9.png"}
     * PageCount : 0
     * Success : 1
     */

    private String ErrorCode;
    private String ExtendObj;
    private String Msg;
    private String OK;
    private ObjBean Obj;
    private int PageCount;
    private String Success;

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public String getExtendObj() {
        return ExtendObj;
    }

    public void setExtendObj(String ExtendObj) {
        this.ExtendObj = ExtendObj;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public String getOK() {
        return OK;
    }

    public void setOK(String OK) {
        this.OK = OK;
    }

    public ObjBean getObj() {
        return Obj;
    }

    public void setObj(ObjBean Obj) {
        this.Obj = Obj;
    }

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int PageCount) {
        this.PageCount = PageCount;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String Success) {
        this.Success = Success;
    }

    public static class ObjBean {
        /**
         * CategoryText :
         * Ext : .png
         * FileCategory : 1
         * Name : test22.png
         * Url : http://139.219.224.246:5587/memberIcons/684708b4-040e-4591-b199-36199ed542f9.png
         */

        private String CategoryText;
        private String Ext;
        private String FileCategory;
        private String Name;
        private String Url;

        public String getCategoryText() {
            return CategoryText;
        }

        public void setCategoryText(String CategoryText) {
            this.CategoryText = CategoryText;
        }

        public String getExt() {
            return Ext;
        }

        public void setExt(String Ext) {
            this.Ext = Ext;
        }

        public String getFileCategory() {
            return FileCategory;
        }

        public void setFileCategory(String FileCategory) {
            this.FileCategory = FileCategory;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }
    }
}
