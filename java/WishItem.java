package com.dongyang.gg;

public class WishItem {


        String pic;
        String name;
        String price;

        public WishItem(String pic, String name, String price){
            this.pic=pic;
            this.name=name;
            this.price=price;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }



        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }

