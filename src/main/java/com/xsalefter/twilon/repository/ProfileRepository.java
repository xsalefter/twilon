package com.xsalefter.twilon.repository;

import com.xsalefter.twilon.entity.profile.Profile;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ProfileRepository extends CassandraRepository<Profile, String> {
}
