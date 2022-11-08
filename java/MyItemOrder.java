package com.dongyang.gg;

public class MyItemOrder {


        String pic;
        String name;
        String price;
        String num;

        public MyItemOrder(String pic, String name, String price, String num){
            this.pic=pic;
            this.name=name;
            this.price=price;
            this.num=num;
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
    public String getNum() {
        return num;
    }

    public void setNum(String pic) {
        this.pic = num;
    }
    }

