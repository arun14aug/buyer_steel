package com.tanzil.steelhub.model;


/**
 * Created by arun on 16/12/15.
 */
public class ModelManager {

    public static ModelManager modelMgr = null;

    private AuthManager authMgr;
    private RequirementManager requirementManager;
    private CommonDataManager commonDataManager;
    private AddressManager addressManager;

    private ModelManager() {

        authMgr = new AuthManager();
        requirementManager = new RequirementManager();
        commonDataManager = new CommonDataManager();
        addressManager = new AddressManager();
    }

    public void clearManagerInstance() {

        this.authMgr = null;
        this.requirementManager = null;
        this.commonDataManager = null;
        this.addressManager = null;
    }

    public static ModelManager getInstance() {
        if (modelMgr == null) {
            modelMgr = new ModelManager();
        }
        return modelMgr;
    }

    public static void setInstance() {
        modelMgr = new ModelManager();
    }

    public static boolean getInstanceModelManager() {
        return modelMgr != null;
    }

    public AuthManager getAuthManager() {

        return this.authMgr;
    }

    public RequirementManager getRequirementManager() {
        return requirementManager;
    }

    public CommonDataManager getCommonDataManager() {
        return commonDataManager;
    }

    public AddressManager getAddressManager() {
        return addressManager;
    }
}
