package com.gk.beans;


import java.util.List;

/**
 * Created by JDRY-SJM on 2017/9/19.
 */

public class HomePageRecommendBean {
    private List<Integer> listCarousel;
    private List<NewManEnterGate> newManEnterGateList;
    private List<NewManEnterGate> skillImproveList;
    private List<NewManEnterGate> advanceSkillList;
    private List<NewManEnterGate> favoriteSkillList;

    public List<Integer> getListCarousel() {
        return listCarousel;
    }

    public void setListCarousel(List<Integer> listCarousel) {
        this.listCarousel = listCarousel;
    }

    public List<NewManEnterGate> getNewManEnterGateList() {
        return newManEnterGateList;
    }

    public void setNewManEnterGateList(List<NewManEnterGate> newManEnterGateList) {
        this.newManEnterGateList = newManEnterGateList;
    }

    public List<NewManEnterGate> getSkillImproveList() {
        return skillImproveList;
    }

    public void setSkillImproveList(List<NewManEnterGate> skillImproveList) {
        this.skillImproveList = skillImproveList;
    }

    public List<NewManEnterGate> getAdvanceSkillList() {
        return advanceSkillList;
    }

    public void setAdvanceSkillList(List<NewManEnterGate> advanceSkillList) {
        this.advanceSkillList = advanceSkillList;
    }

    public List<NewManEnterGate> getFavoriteSkillList() {
        return favoriteSkillList;
    }

    public void setFavoriteSkillList(List<NewManEnterGate> favoriteSkillList) {
        this.favoriteSkillList = favoriteSkillList;
    }

    public static class NewManEnterGate{
        private String imageUrl;
        private String description;
        private String price;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
