package com.brightgreenstar.accounting.entities;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by peter on 28/01/2016.
 */

@Entity
@Cache

public class Invoice {
    @Id //id fields can be long, Long or String. Only Long automatically generates keys when it s null.  We will use a string and set the id manual to an invoice reference
    private String id;

    // Amount.  Stored in cents/pennies i.e. £9.99 is stored as 999

    private long amountCents;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getAmountCents()
    {
        return amountCents;
    }

    public void setAmountCents(long amountCents)
    {
        this.amountCents = amountCents;
    }

}
