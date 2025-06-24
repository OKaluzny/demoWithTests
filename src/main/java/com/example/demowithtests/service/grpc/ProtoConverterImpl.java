package com.example.demowithtests.service.grpc;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Document;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class ProtoConverterImpl implements ProtoConverter {

    @Override
    public Employee toEmployee(CreateEmployeeRequest request) {
        return Employee.builder()
                .name(request.getName())
                .country(request.getCountry())
                .email(request.getEmail())
                .gender(convertGenderProtoToGender(request.getGender()))
                .addresses(convertAddressProtoSetToAddressSet(request.getAddressesList()))
                .document(request.hasDocument() ? convertDocumentProtoToDocument(request.getDocument()) : null)
                .build();
    }

    @Override
    public Employee toEmployee(UpdateEmployeeRequest request) {
        return Employee.builder()
                .id(request.getId())
                .name(request.getName())
                .country(request.getCountry())
                .email(request.getEmail())
                .gender(convertGenderProtoToGender(request.getGender()))
                .addresses(convertAddressProtoSetToAddressSet(request.getAddressesList()))
                .document(request.hasDocument() ? convertDocumentProtoToDocument(request.getDocument()) : null)
                .build();
    }

    @Override
    public EmployeeProto toEmployeeProto(Employee employee) {
        EmployeeProto.Builder builder = EmployeeProto.newBuilder()
                .setId(employee.getId())
                .setName(employee.getName())
                .setCountry(employee.getCountry() != null ? employee.getCountry() : "")
                .setEmail(employee.getEmail() != null ? employee.getEmail() : "");

        if (employee.getGender() != null) {
            builder.setGender(convertGenderToGenderProto(employee.getGender()));
        }

        if (employee.getAddresses() != null && !employee.getAddresses().isEmpty()) {
            employee.getAddresses().forEach(address -> 
                builder.addAddresses(convertAddressToAddressProto(address))
            );
        }

        if (employee.getDocument() != null) {
            builder.setDocument(convertDocumentToDocumentProto(employee.getDocument()));
        }

        return builder.build();
    }

    private AddressProto convertAddressToAddressProto(Address address) {
        return AddressProto.newBuilder()
                .setId(address.getId() != null ? address.getId() : 0)
                .setAddressHasActive(address.getAddressHasActive() != null ? address.getAddressHasActive() : false)
                .setCountry(address.getCountry() != null ? address.getCountry() : "")
                .setCity(address.getCity() != null ? address.getCity() : "")
                .setStreet(address.getStreet() != null ? address.getStreet() : "")
                .build();
    }

    private DocumentProto convertDocumentToDocumentProto(Document document) {
        DocumentProto.Builder builder = DocumentProto.newBuilder()
                .setId(document.getId() != null ? document.getId() : 0)
                .setNumber(document.getNumber() != null ? document.getNumber() : "")
                .setUuid(document.getUuid() != null ? document.getUuid() : "")
                .setIsHandled(document.getIsHandled() != null ? document.getIsHandled() : false);

        if (document.getExpireDate() != null) {
            builder.setExpireDate(document.getExpireDate().toString());
        }

        return builder.build();
    }

    private GenderProto convertGenderToGenderProto(Gender gender) {
        return gender == Gender.M ? GenderProto.MALE : GenderProto.FEMALE;
    }

    private Gender convertGenderProtoToGender(GenderProto genderProto) {
        return genderProto == GenderProto.MALE ? Gender.M : Gender.F;
    }

    private Set<Address> convertAddressProtoSetToAddressSet(java.util.List<AddressProto> addressProtoList) {
        Set<Address> addresses = new HashSet<>();

        for (AddressProto addressProto : addressProtoList) {
            Address address = Address.builder()
                    .id(addressProto.getId())
                    .addressHasActive(addressProto.getAddressHasActive())
                    .country(addressProto.getCountry())
                    .city(addressProto.getCity())
                    .street(addressProto.getStreet())
                    .build();
            addresses.add(address);
        }

        return addresses;
    }

    private Document convertDocumentProtoToDocument(DocumentProto documentProto) {
        Document.DocumentBuilder builder = Document.builder()
                .number(documentProto.getNumber())
                .uuid(documentProto.getUuid())
                .isHandled(documentProto.getIsHandled());

        if (documentProto.getId() != 0) {
            builder.id(documentProto.getId());
        }

        if (!documentProto.getExpireDate().isEmpty()) {
            try {
                LocalDateTime expireDate = LocalDateTime.parse(documentProto.getExpireDate());
                builder.expireDate(expireDate);
            } catch (Exception e) {
                log.warn("Could not parse expire date: {}", documentProto.getExpireDate());
            }
        }

        return builder.build();
    }
}