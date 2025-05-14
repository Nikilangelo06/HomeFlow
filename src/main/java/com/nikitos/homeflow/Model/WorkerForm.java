package com.nikitos.homeflow.Model;

public class WorkerForm {
    private int workerFormId;
    private int workerId;
    private int formId;

    public WorkerForm(int workerFormId, int workerId, int formId) {
        this.workerFormId = workerFormId;
        this.workerId = workerId;
        this.formId = formId;
    }

    public WorkerForm(int workerId, int formId) {
        this.workerId = workerId;
        this.formId = formId;
    }


    public int getWorkerFormId() {
        return workerFormId;
    }
    public int getWorker_id() {
        return workerId;
    };
    public int getFormId() {
        return formId;
    }

    public void setWorkerFormId(int workerFormId) {
        this.workerFormId = workerFormId;
    }
    public void setWorker_id(int workerId) {
        this.workerId = workerId;
    }
    public void setFormId(int formId) {
        this.formId = formId;
    }
}
