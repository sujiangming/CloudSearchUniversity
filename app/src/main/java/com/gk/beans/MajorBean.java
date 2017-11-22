package com.gk.beans;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/22.
 */

public class MajorBean {

    /**
     * data : [{"code":"111","id":"2c948a825f58d5a6015f58d8683d0002","name":"本科专业","nodes":[{"code":"001","id":"2c948a825f58d5a6015f58dcc86a0004","name":"哲学","nodes":[{"code":"2222","id":"2c948a825f8a6e94015f8a70d9aa0000","name":"哲学类","nodes":[{"code":"1","id":"402881fb5fce47d2015fce6030ca0001","name":"哲学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"2","id":"402881fb5fce61ac015fce6e01870000","name":"逻辑学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"3","id":"402881fb5fce61ac015fce915c700004","name":"宗教学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"4","id":"402881fb5fce61ac015fcec6c0140005","name":"伦理学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"}],"pid":"2c948a825f58d5a6015f58dcc86a0004"}],"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"002","id":"2c948a825f58d5a6015f58dd0f070005","name":"经济学","nodes":[{"code":"1020","id":"2c948a825f8afe39015f8c3572360001","name":"经济与贸易类","nodes":[{"code":"10100","id":"402881fb5fce61ac015fcec701100006","name":"贸易经济","nodes":null,"pid":"2c948a825f8afe39015f8c3572360001"},{"code":"10101","id":"402881fb5fce61ac015fcec76bbd0007","name":"国际经济与贸易","nodes":null,"pid":"2c948a825f8afe39015f8c3572360001"}],"pid":"2c948a825f58d5a6015f58dd0f070005"},{"code":"11111","id":"2c948a825f8afe39015f8c3595ac0002","name":"财政学类","nodes":null,"pid":"2c948a825f58d5a6015f58dd0f070005"},{"code":"11111","id":"2c948a825f8afe39015f8c35d4170003","name":"金融学类","nodes":null,"pid":"2c948a825f58d5a6015f58dd0f070005"},{"code":"11111","id":"2c948a825f8afe39015f8c35fa380004","name":"经济学类","nodes":null,"pid":"2c948a825f58d5a6015f58dd0f070005"}],"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"003","id":"2c948a825f58e4a6015f58e8b32b0000","name":"法学","nodes":[{"code":"11111","id":"2c948a825f8afe39015f8c3631de0005","name":"社会学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c365e780006","name":"民族学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c36823a0007","name":"法学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c36afc30008","name":"政治学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c36f3a60009","name":"马克思主义理论类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c372172000a","name":"公安学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"}],"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"004","id":"2c948a825f58e4a6015f58e8e0500001","name":"教育学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"005","id":"2c948a825f58e4a6015f58e90e030002","name":"文学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"006","id":"2c948a825f58e4a6015f58e93a880003","name":"历史学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"007","id":"2c948a825f58e4a6015f58e966350004","name":"理学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"008","id":"2c948a825f58e4a6015f58e997e30005","name":"工学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"009","id":"2c948a825f58e4a6015f58e9b8610006","name":"农学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"010","id":"2c948a825f58e4a6015f58ea450d0007","name":"医学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"011","id":"2c948a825f58e4a6015f58ea73490008","name":"管理学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"012","id":"2c948a825f58e4a6015f58ea95f70009","name":"艺术学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"}],"pid":null},{"code":"101","id":"2c948a825f58d5a6015f58d8be360003","name":"专科专业","nodes":[{"code":"1001","id":"2c948a825f58e4a6015f58eb3d51000a","name":"农林牧渔大类","nodes":[{"code":"11","id":"402881fb5fce61ac015fcec7e8ac0008","name":"农业类","nodes":[{"code":"11","id":"402881fb5fce61ac015fcec85ccb0009","name":"作物生产技术","nodes":null,"pid":"402881fb5fce61ac015fcec7e8ac0008"}],"pid":"2c948a825f58e4a6015f58eb3d51000a"}],"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1002","id":"2c948a825f58e4a6015f59022cb80020","name":"资源环境与安全大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1003","id":"2c948a825f58e4a6015f59027c0a0021","name":"能源动力与材料大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1004","id":"2c948a825f58e4a6015f5902b3c80022","name":"土木建筑大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1005","id":"2c948a825f58e4a6015f5902e0110023","name":"水利大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1006","id":"2c948a825f58e4a6015f59032c8a0024","name":"装备制造大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1007","id":"2c948a825f58e4a6015f590366960025","name":"生物与化工大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1008","id":"2c948a825f58e4a6015f5903a5240026","name":"轻工纺织大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1009","id":"2c948a825f58e4a6015f5903eca30027","name":"食品药品与粮食大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1010","id":"2c948a825f58e4a6015f590428210028","name":"交通运输大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1011","id":"2c948a825f58e4a6015f59045a970029","name":"电子信息大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1012","id":"2c948a825f58e4a6015f59048da9002a","name":"医药卫生大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1013","id":"2c948a825f58e4a6015f5904dab6002b","name":"财经商贸大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1014","id":"2c948a825f58e4a6015f5905035e002c","name":"旅游大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1015","id":"2c948a825f58e4a6015f590539ba002d","name":"文化艺术大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1016","id":"2c948a825f58e4a6015f59057371002e","name":"新闻传播大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1017","id":"2c948a825f58e4a6015f5905a523002f","name":"教育与体育大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1018","id":"2c948a825f58e4a6015f5905e0030030","name":"公安与司法大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1019","id":"2c948a825f58e4a6015f59061a9f0031","name":"公共管理与服务大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"}],"pid":null},{"code":"001","id":"2c948a825f58d5a6015f58dcc86a0004","name":"哲学","nodes":[{"code":"2222","id":"2c948a825f8a6e94015f8a70d9aa0000","name":"哲学类","nodes":[{"code":"1","id":"402881fb5fce47d2015fce6030ca0001","name":"哲学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"2","id":"402881fb5fce61ac015fce6e01870000","name":"逻辑学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"3","id":"402881fb5fce61ac015fce915c700004","name":"宗教学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"4","id":"402881fb5fce61ac015fcec6c0140005","name":"伦理学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"}],"pid":"2c948a825f58d5a6015f58dcc86a0004"}],"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"002","id":"2c948a825f58d5a6015f58dd0f070005","name":"经济学","nodes":[{"code":"1020","id":"2c948a825f8afe39015f8c3572360001","name":"经济与贸易类","nodes":[{"code":"10100","id":"402881fb5fce61ac015fcec701100006","name":"贸易经济","nodes":null,"pid":"2c948a825f8afe39015f8c3572360001"},{"code":"10101","id":"402881fb5fce61ac015fcec76bbd0007","name":"国际经济与贸易","nodes":null,"pid":"2c948a825f8afe39015f8c3572360001"}],"pid":"2c948a825f58d5a6015f58dd0f070005"},{"code":"11111","id":"2c948a825f8afe39015f8c3595ac0002","name":"财政学类","nodes":null,"pid":"2c948a825f58d5a6015f58dd0f070005"},{"code":"11111","id":"2c948a825f8afe39015f8c35d4170003","name":"金融学类","nodes":null,"pid":"2c948a825f58d5a6015f58dd0f070005"},{"code":"11111","id":"2c948a825f8afe39015f8c35fa380004","name":"经济学类","nodes":null,"pid":"2c948a825f58d5a6015f58dd0f070005"}],"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"003","id":"2c948a825f58e4a6015f58e8b32b0000","name":"法学","nodes":[{"code":"11111","id":"2c948a825f8afe39015f8c3631de0005","name":"社会学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c365e780006","name":"民族学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c36823a0007","name":"法学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c36afc30008","name":"政治学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c36f3a60009","name":"马克思主义理论类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c372172000a","name":"公安学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"}],"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"004","id":"2c948a825f58e4a6015f58e8e0500001","name":"教育学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"005","id":"2c948a825f58e4a6015f58e90e030002","name":"文学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"006","id":"2c948a825f58e4a6015f58e93a880003","name":"历史学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"007","id":"2c948a825f58e4a6015f58e966350004","name":"理学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"008","id":"2c948a825f58e4a6015f58e997e30005","name":"工学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"009","id":"2c948a825f58e4a6015f58e9b8610006","name":"农学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"010","id":"2c948a825f58e4a6015f58ea450d0007","name":"医学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"011","id":"2c948a825f58e4a6015f58ea73490008","name":"管理学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"012","id":"2c948a825f58e4a6015f58ea95f70009","name":"艺术学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"1001","id":"2c948a825f58e4a6015f58eb3d51000a","name":"农林牧渔大类","nodes":[{"code":"11","id":"402881fb5fce61ac015fcec7e8ac0008","name":"农业类","nodes":[{"code":"11","id":"402881fb5fce61ac015fcec85ccb0009","name":"作物生产技术","nodes":null,"pid":"402881fb5fce61ac015fcec7e8ac0008"}],"pid":"2c948a825f58e4a6015f58eb3d51000a"}],"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1002","id":"2c948a825f58e4a6015f59022cb80020","name":"资源环境与安全大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1003","id":"2c948a825f58e4a6015f59027c0a0021","name":"能源动力与材料大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1004","id":"2c948a825f58e4a6015f5902b3c80022","name":"土木建筑大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1005","id":"2c948a825f58e4a6015f5902e0110023","name":"水利大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1006","id":"2c948a825f58e4a6015f59032c8a0024","name":"装备制造大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1007","id":"2c948a825f58e4a6015f590366960025","name":"生物与化工大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1008","id":"2c948a825f58e4a6015f5903a5240026","name":"轻工纺织大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1009","id":"2c948a825f58e4a6015f5903eca30027","name":"食品药品与粮食大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1010","id":"2c948a825f58e4a6015f590428210028","name":"交通运输大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1011","id":"2c948a825f58e4a6015f59045a970029","name":"电子信息大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1012","id":"2c948a825f58e4a6015f59048da9002a","name":"医药卫生大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1013","id":"2c948a825f58e4a6015f5904dab6002b","name":"财经商贸大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1014","id":"2c948a825f58e4a6015f5905035e002c","name":"旅游大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1015","id":"2c948a825f58e4a6015f590539ba002d","name":"文化艺术大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1016","id":"2c948a825f58e4a6015f59057371002e","name":"新闻传播大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1017","id":"2c948a825f58e4a6015f5905a523002f","name":"教育与体育大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1018","id":"2c948a825f58e4a6015f5905e0030030","name":"公安与司法大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"1019","id":"2c948a825f58e4a6015f59061a9f0031","name":"公共管理与服务大类","nodes":null,"pid":"2c948a825f58d5a6015f58d8be360003"},{"code":"2222","id":"2c948a825f8a6e94015f8a70d9aa0000","name":"哲学类","nodes":[{"code":"1","id":"402881fb5fce47d2015fce6030ca0001","name":"哲学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"2","id":"402881fb5fce61ac015fce6e01870000","name":"逻辑学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"3","id":"402881fb5fce61ac015fce915c700004","name":"宗教学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"4","id":"402881fb5fce61ac015fcec6c0140005","name":"伦理学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"}],"pid":"2c948a825f58d5a6015f58dcc86a0004"},{"code":"1020","id":"2c948a825f8afe39015f8c3572360001","name":"经济与贸易类","nodes":[{"code":"10100","id":"402881fb5fce61ac015fcec701100006","name":"贸易经济","nodes":null,"pid":"2c948a825f8afe39015f8c3572360001"},{"code":"10101","id":"402881fb5fce61ac015fcec76bbd0007","name":"国际经济与贸易","nodes":null,"pid":"2c948a825f8afe39015f8c3572360001"}],"pid":"2c948a825f58d5a6015f58dd0f070005"},{"code":"11111","id":"2c948a825f8afe39015f8c3595ac0002","name":"财政学类","nodes":null,"pid":"2c948a825f58d5a6015f58dd0f070005"},{"code":"11111","id":"2c948a825f8afe39015f8c35d4170003","name":"金融学类","nodes":null,"pid":"2c948a825f58d5a6015f58dd0f070005"},{"code":"11111","id":"2c948a825f8afe39015f8c35fa380004","name":"经济学类","nodes":null,"pid":"2c948a825f58d5a6015f58dd0f070005"},{"code":"11111","id":"2c948a825f8afe39015f8c3631de0005","name":"社会学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c365e780006","name":"民族学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c36823a0007","name":"法学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c36afc30008","name":"政治学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c36f3a60009","name":"马克思主义理论类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c372172000a","name":"公安学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"1","id":"402881fb5fce47d2015fce6030ca0001","name":"哲学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"2","id":"402881fb5fce61ac015fce6e01870000","name":"逻辑学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"3","id":"402881fb5fce61ac015fce915c700004","name":"宗教学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"4","id":"402881fb5fce61ac015fcec6c0140005","name":"伦理学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"10100","id":"402881fb5fce61ac015fcec701100006","name":"贸易经济","nodes":null,"pid":"2c948a825f8afe39015f8c3572360001"},{"code":"10101","id":"402881fb5fce61ac015fcec76bbd0007","name":"国际经济与贸易","nodes":null,"pid":"2c948a825f8afe39015f8c3572360001"},{"code":"11","id":"402881fb5fce61ac015fcec7e8ac0008","name":"农业类","nodes":[{"code":"11","id":"402881fb5fce61ac015fcec85ccb0009","name":"作物生产技术","nodes":null,"pid":"402881fb5fce61ac015fcec7e8ac0008"}],"pid":"2c948a825f58e4a6015f58eb3d51000a"},{"code":"11","id":"402881fb5fce61ac015fcec85ccb0009","name":"作物生产技术","nodes":null,"pid":"402881fb5fce61ac015fcec7e8ac0008"}]
     * message : 查询成功
     * status : 1
     */

    private String message;
    private int status;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : 111
         * id : 2c948a825f58d5a6015f58d8683d0002
         * name : 本科专业
         * nodes : [{"code":"001","id":"2c948a825f58d5a6015f58dcc86a0004","name":"哲学","nodes":[{"code":"2222","id":"2c948a825f8a6e94015f8a70d9aa0000","name":"哲学类","nodes":[{"code":"1","id":"402881fb5fce47d2015fce6030ca0001","name":"哲学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"2","id":"402881fb5fce61ac015fce6e01870000","name":"逻辑学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"3","id":"402881fb5fce61ac015fce915c700004","name":"宗教学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"4","id":"402881fb5fce61ac015fcec6c0140005","name":"伦理学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"}],"pid":"2c948a825f58d5a6015f58dcc86a0004"}],"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"002","id":"2c948a825f58d5a6015f58dd0f070005","name":"经济学","nodes":[{"code":"1020","id":"2c948a825f8afe39015f8c3572360001","name":"经济与贸易类","nodes":[{"code":"10100","id":"402881fb5fce61ac015fcec701100006","name":"贸易经济","nodes":null,"pid":"2c948a825f8afe39015f8c3572360001"},{"code":"10101","id":"402881fb5fce61ac015fcec76bbd0007","name":"国际经济与贸易","nodes":null,"pid":"2c948a825f8afe39015f8c3572360001"}],"pid":"2c948a825f58d5a6015f58dd0f070005"},{"code":"11111","id":"2c948a825f8afe39015f8c3595ac0002","name":"财政学类","nodes":null,"pid":"2c948a825f58d5a6015f58dd0f070005"},{"code":"11111","id":"2c948a825f8afe39015f8c35d4170003","name":"金融学类","nodes":null,"pid":"2c948a825f58d5a6015f58dd0f070005"},{"code":"11111","id":"2c948a825f8afe39015f8c35fa380004","name":"经济学类","nodes":null,"pid":"2c948a825f58d5a6015f58dd0f070005"}],"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"003","id":"2c948a825f58e4a6015f58e8b32b0000","name":"法学","nodes":[{"code":"11111","id":"2c948a825f8afe39015f8c3631de0005","name":"社会学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c365e780006","name":"民族学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c36823a0007","name":"法学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c36afc30008","name":"政治学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c36f3a60009","name":"马克思主义理论类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"},{"code":"11111","id":"2c948a825f8afe39015f8c372172000a","name":"公安学类","nodes":null,"pid":"2c948a825f58e4a6015f58e8b32b0000"}],"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"004","id":"2c948a825f58e4a6015f58e8e0500001","name":"教育学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"005","id":"2c948a825f58e4a6015f58e90e030002","name":"文学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"006","id":"2c948a825f58e4a6015f58e93a880003","name":"历史学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"007","id":"2c948a825f58e4a6015f58e966350004","name":"理学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"008","id":"2c948a825f58e4a6015f58e997e30005","name":"工学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"009","id":"2c948a825f58e4a6015f58e9b8610006","name":"农学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"010","id":"2c948a825f58e4a6015f58ea450d0007","name":"医学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"011","id":"2c948a825f58e4a6015f58ea73490008","name":"管理学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"},{"code":"012","id":"2c948a825f58e4a6015f58ea95f70009","name":"艺术学","nodes":null,"pid":"2c948a825f58d5a6015f58d8683d0002"}]
         * pid : null
         */

        private String code;
        private String id;
        private String name;
        private String pid;
        private List<NodesBeanXX> nodes;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public List<NodesBeanXX> getNodes() {
            return nodes;
        }

        public void setNodes(List<NodesBeanXX> nodes) {
            this.nodes = nodes;
        }

        public static class NodesBeanXX {
            /**
             * code : 001
             * id : 2c948a825f58d5a6015f58dcc86a0004
             * name : 哲学
             * nodes : [{"code":"2222","id":"2c948a825f8a6e94015f8a70d9aa0000","name":"哲学类","nodes":[{"code":"1","id":"402881fb5fce47d2015fce6030ca0001","name":"哲学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"2","id":"402881fb5fce61ac015fce6e01870000","name":"逻辑学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"3","id":"402881fb5fce61ac015fce915c700004","name":"宗教学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"4","id":"402881fb5fce61ac015fcec6c0140005","name":"伦理学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"}],"pid":"2c948a825f58d5a6015f58dcc86a0004"}]
             * pid : 2c948a825f58d5a6015f58d8683d0002
             */

            private String code;
            private String id;
            private String name;
            private String pid;
            private List<NodesBeanX> nodes;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
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

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public List<NodesBeanX> getNodes() {
                return nodes;
            }

            public void setNodes(List<NodesBeanX> nodes) {
                this.nodes = nodes;
            }

            public static class NodesBeanX {
                /**
                 * code : 2222
                 * id : 2c948a825f8a6e94015f8a70d9aa0000
                 * name : 哲学类
                 * nodes : [{"code":"1","id":"402881fb5fce47d2015fce6030ca0001","name":"哲学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"2","id":"402881fb5fce61ac015fce6e01870000","name":"逻辑学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"3","id":"402881fb5fce61ac015fce915c700004","name":"宗教学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"},{"code":"4","id":"402881fb5fce61ac015fcec6c0140005","name":"伦理学","nodes":null,"pid":"2c948a825f8a6e94015f8a70d9aa0000"}]
                 * pid : 2c948a825f58d5a6015f58dcc86a0004
                 */

                private String code;
                private String id;
                private String name;
                private String pid;
                private List<NodesBean> nodes;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
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

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }

                public List<NodesBean> getNodes() {
                    return nodes;
                }

                public void setNodes(List<NodesBean> nodes) {
                    this.nodes = nodes;
                }

                public static class NodesBean {
                    /**
                     * code : 1
                     * id : 402881fb5fce47d2015fce6030ca0001
                     * name : 哲学
                     * nodes : null
                     * pid : 2c948a825f8a6e94015f8a70d9aa0000
                     */

                    private String code;
                    private String id;
                    private String name;
                    private String nodes;
                    private String pid;

                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
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

                    public String getNodes() {
                        return nodes;
                    }

                    public void setNodes(String nodes) {
                        this.nodes = nodes;
                    }

                    public String getPid() {
                        return pid;
                    }

                    public void setPid(String pid) {
                        this.pid = pid;
                    }
                }
            }
        }
    }
}
