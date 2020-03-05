package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vdoers.dynamiccontrolslibrary.Utils.Permissions;
import com.vdoers.dynamiccontrolslibrary.mControls.models.FileSavedModel;
import com.vdoers.dynamiccontrolslibrary.mControls.models.ImageSavedModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class JsonWorkflowList implements Serializable {

    @SerializedName("CaseTypeId")
    @Expose
    private int caseTypeId;
    @SerializedName("WorkflowId")
    @Expose
    private int workflowId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("subforms")
    @Expose
    private List<Subform> subforms = null;

    @SerializedName("IsMandatory")
    @Expose
    private boolean isMandatory;

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }


    private boolean isCompleted;

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }


    public int getCaseTypeId() {
        return caseTypeId;
    }

    public void setCaseTypeId(int caseTypeId) {
        this.caseTypeId = caseTypeId;
    }

    public int getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(int workflowId) {
        this.workflowId = workflowId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Subform> getSubforms() {
        return subforms;
    }

    public void setSubforms(List<Subform> subforms) {
        this.subforms = subforms;
    }

    public class Subform implements Serializable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("back")
        @Expose
        private String back;
        @SerializedName("next")
        @Expose
        private String next;
        @SerializedName("gotoId")
        @Expose
        private String gotoId;
        @SerializedName("fields")
        @Expose
        private List<Field> fields = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBack() {
            return back;
        }

        public void setBack(String back) {
            this.back = back;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getGotoId() {
            return gotoId;
        }

        public void setGotoId(String gotoId) {
            this.gotoId = gotoId;
        }

        public List<Field> getFields() {
            return fields;
        }

        public void setFields(List<Field> fields) {
            this.fields = fields;
        }

       /* @Override
        public Object clone() throws CloneNotSupportedException {
            JsonWorkflowList.Subform subform = (JsonWorkflowList.Subform) super.clone();
            if (fields != null) {
                List<Field> alField = new ArrayList<>();
                subform.setFields(alField);
                for (int idx = 0; idx < fields.size(); idx++) {
                    JsonWorkflowList.Field field = (JsonWorkflowList.Field) fields.get(idx).clone();
                    alField.add(field);
                }

            }
            return subform;
        }*/

    }

    public class Field implements Serializable {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("required")
        @Expose
        private String required;
        @SerializedName("maxLength")
        @Expose
        private int maxLength;

        @SerializedName("Regex")
        @Expose
        private String regex;

        @SerializedName("optionList")
        @Expose
        private List<OptionList> optionList = null;

        @SerializedName("ReadMoreURL")
        @Expose
        private String readMoreURL;

        @SerializedName("IsAllCaps")
        @Expose
        private boolean isAllCaps;

        @SerializedName("minLength")
        @Expose
        private int minLength;

        @SerializedName("IsEditable")
        @Expose
        private boolean isEditable;
       /* @SerializedName("DefaultValue")
        @Expose
        private String defaultValue;

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }*/

        public boolean isEditable() {
            return isEditable;
        }

        public void setEditable(boolean editable) {
            isEditable = editable;
        }

        public int getMinLength() {
            return minLength;
        }

        public void setMinLength(int minLength) {
            this.minLength = minLength;
        }


        public boolean isAllCaps() {
            return isAllCaps;
        }

        public void setAllCaps(boolean allCaps) {
            isAllCaps = allCaps;
        }


        public String getReadMoreURL() {
            return readMoreURL;
        }

        public void setReadMoreURL(String readMoreURL) {
            this.readMoreURL = readMoreURL;
        }


        private Object obj;
        private Object answer = "";
        // private String fieldValue = "";
        private String editableValue = "";
        private String bitMapString = "";

        private List<ImageSavedModel> imageSavedModelList = new ArrayList<>();
        private List<FileSavedModel> fileSavedModelList = new ArrayList<>();

        public List<ImageSavedModel> getImageSavedModelList() {
            return imageSavedModelList;
        }

        public void setImageSavedModelList(ImageSavedModel imageSavedModel) {
            if (imageSavedModelList == null) {
                imageSavedModelList = new ArrayList<>();
            }
            this.imageSavedModelList.add(imageSavedModel);
        }

        public List<FileSavedModel> getFileSavedModelList() {
            return fileSavedModelList;
        }

        public void setFileSavedModelList(FileSavedModel fileSavedModel) {
            if (fileSavedModelList == null) {
                fileSavedModelList = new ArrayList<>();
            }
            this.fileSavedModelList.add(fileSavedModel);
        }

        public String getRegex() {
            return regex;
        }

        public void setRegex(String regex) {
            this.regex = regex;
        }

        public String getBitMapString() {
            return bitMapString;
        }

        public void setBitMapString(String bitMapString) {
            this.bitMapString = bitMapString;
        }


        public String getEditableValue() {
            return editableValue;
        }

        public void setEditableValue(String editableValue) {
            this.editableValue = editableValue;
        }

/*

        public String getFieldValue() {
            return fieldValue;
        }

        public void setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
        }
*/


        public Object getAnswer() {
            return answer;
        }


        public void setAnswer(Object answer, Field field) {
            if (!field.getType().equalsIgnoreCase(Types.BUTTON_TYPE))
                Permissions.dataObject.put(this.name, answer);
            this.answer = answer;

        }

        public void setBooleanAnswer(boolean answer, Field field) {
            Permissions.dataObject.put(this.name, answer);
        }


        public Object getObject() {
            return obj;
        }

        public void setObject(Object obj) {
            this.obj = obj;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRequired() {
            return required;
        }

        public void setRequired(String required) {
            this.required = required;
        }

        public int getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(int maxLength) {
            this.maxLength = maxLength;
        }

        public List<OptionList> getOptionList() {
            return optionList;
        }

        public void setOptionList(List<OptionList> optionList) {
            this.optionList = optionList;
        }

      /*  @Override
        public Object clone() throws CloneNotSupportedException {
            JsonWorkflowList.Field field = (JsonWorkflowList.Field) super.clone();
       *//* if (optionAL != null) {
            ArrayList<Option> options = new ArrayList<Option>();
            field.setOptionAL(options);
            for (int idx = 0; idx < optionAL.size(); idx++) {
                options.add((Option) optionAL.get(idx).clone());
            }
        }*//*
            return field;
        }*/

    }

    public class OptionList implements Serializable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("value")
        @Expose
        private String value;
        @SerializedName("gotoId")
        @Expose
        private String gotoId;

        @SerializedName("isChecked")
        @Expose
        private boolean isChecked;

        public boolean getIsChecked() {
            return isChecked;
        }

        public void setIsChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getGotoId() {
            return gotoId;
        }

        public void setGotoId(String gotoId) {
            this.gotoId = gotoId;
        }

    }


}
