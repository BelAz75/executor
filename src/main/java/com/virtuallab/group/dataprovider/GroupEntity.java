package com.virtuallab.group.dataprovider;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
public class GroupEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private String id;

    @Column(name = "name", unique = true)
    private String name;

    @ElementCollection(targetClass = GroupMembershipEntity.class)
    @JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "group_uuid", referencedColumnName = "uuid"))
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SUBSELECT)
    private Set<GroupMembershipEntity> memberships = new HashSet<>();

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

    public Set<GroupMembershipEntity> getMemberships() {
        return memberships;
    }

    public void setMemberships(Set<GroupMembershipEntity> memberships) {
        this.memberships = memberships;
    }

    public void updateMemberships(Set<GroupMembershipEntity> newMemberships) {
        memberships.clear();
        memberships.addAll(newMemberships);
    }

}
