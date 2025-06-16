package net.kkennib.house.member.mapper;

import net.kkennib.house.member.dto.MemberDto;
import net.kkennib.house.member.dto.MemberResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
  MemberResponse toResponse(MemberDto dto);
}
