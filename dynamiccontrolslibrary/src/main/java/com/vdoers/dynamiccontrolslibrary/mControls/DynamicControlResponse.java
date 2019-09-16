package com.vdoers.dynamiccontrolslibrary.mControls;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vdoers.dynamiccontrolslibrary.Utils.Permissions;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.Types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class DynamicControlResponse {
    @SerializedName("form")
    @Expose
    private Form form;
    @SerializedName("ResponseCode")
    @Expose
    private int responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public class Form {

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

    }


    public class Subform implements Serializable, Cloneable {

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


        @Override
        public Object clone() throws CloneNotSupportedException {
            Subform subform = (Subform) super.clone();
            if (fields != null) {
                List<Field> alField = new ArrayList<>();
                subform.setFields(alField);
                for (int idx = 0; idx < fields.size(); idx++) {
                    Field field = (Field) fields.get(idx).clone();
                    alField.add(field);
                }

            }
            return subform;
        }

    }

    public class Field implements Serializable, Cloneable {

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
        @SerializedName("options")
        @Expose
        private Object options;

        @SerializedName("optionList")
        @Expose
        private List<OptionList> optionList = null;
        @SerializedName("editable")
        @Expose
        private boolean editable;

        @SerializedName("maxLength")
        @Expose
        private int maxLength;


        private Object obj;
        private String answer = "";
        private String fieldValue = "";
        private String editableValue = "";


        public List<OptionList> getOptionList() {
            return optionList;
        }

        public void setOptionList(List<OptionList> optionList) {
            this.optionList = optionList;
        }

        public String getEditableValue() {
            return editableValue;
        }

        public void setEditableValue(String editableValue) {
            this.editableValue = editableValue;
        }


        public String getFieldValue() {
            return fieldValue;
        }

        public void setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
        }


        public String getAnswer() {
            return answer;
        }


        public void setAnswer(String answer, DynamicControlResponse.Field field) {
            try {

                if (field != null) {
                    if (!field.getType().equalsIgnoreCase(Types.BUTTON_TYPE))
                        Permissions.dataObject.put(this.name, answer);
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
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

        public Object getOptions() {
            return options;
        }

        public void setOptions(Object options) {
            this.options = options;
        }


        public boolean getEditable() {
            return editable;
        }

        public void setEditable(boolean editable) {
            this.editable = editable;
        }

        public int getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(int maxLength) {
            this.maxLength = maxLength;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            Field field = (Field) super.clone();
       /* if (optionAL != null) {
            ArrayList<Option> options = new ArrayList<Option>();
            field.setOptionAL(options);
            for (int idx = 0; idx < optionAL.size(); idx++) {
                options.add((Option) optionAL.get(idx).clone());
            }
        }*/
            return field;
        }


    }

    public class OptionList {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("value")
        @Expose
        private String value;
        @SerializedName("gotoId")
        @Expose
        private String gotoId;

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
