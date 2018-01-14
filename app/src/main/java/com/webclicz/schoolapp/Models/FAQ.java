package com.webclicz.schoolapp.Models;

/**
 * Created by VIB-DEV-LINGU on 14-01-2018.
 */

public class FAQ {
    public String FAQID;
    public String FAQ;
    public String FAQDetail;

    public FAQ(String faqid, String faq, String faqDetail) {
        setFAQID(faqid);
        setFAQ(faq);
        setFAQDetail(faqDetail);
    }

    public String getFAQID() {
        return FAQID;
    }

    public void setFAQID(String FAQID) {
        this.FAQID = FAQID;
    }

    public String getFAQ() {
        return FAQ;
    }

    public void setFAQ(String FAQ) {
        this.FAQ = FAQ;
    }

    public String getFAQDetail() {
        return FAQDetail;
    }

    public void setFAQDetail(String FAQDetail) {
        this.FAQDetail = FAQDetail;
    }

}
