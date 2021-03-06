/*
 * Copyright (c) 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.illyasviel.elide.spring.boot.domain;

import com.yahoo.elide.annotation.Include;
import com.yahoo.elide.annotation.OnCreatePreCommit;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.illyasviel.elide.spring.boot.bean.PasswordEncoder;
import org.illyasviel.elide.spring.boot.bean.UsernameEncoder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author olOwOlo
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Include(rootLevel = true)
public class Account {

  private Integer id;
  private String username;
  private String password;

  @Inject
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UsernameEncoder usernameEncoder;

  @Id
  @GeneratedValue(generator = "account_g", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "account_g", sequenceName = "account_sequence", allocationSize = 1)
  public Integer getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  @OnCreatePreCommit
  public void onCreatePreCommit() {
    setUsername(usernameEncoder.encode(getUsername()));
    setPassword(passwordEncoder.encode(getPassword()));
  }
}
