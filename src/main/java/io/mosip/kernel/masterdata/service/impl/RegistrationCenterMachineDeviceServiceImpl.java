 package io.mosip.kernel.masterdata.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import io.mosip.kernel.core.dataaccess.exception.DataAccessLayerException;
import io.mosip.kernel.masterdata.constant.RegistrationCenterMachineDeviceErrorCode;
import io.mosip.kernel.masterdata.dto.RegistrationCenterMachineDeviceDto;
import io.mosip.kernel.masterdata.dto.RequestDto;
import io.mosip.kernel.masterdata.dto.ResponseRrgistrationCenterMachineDeviceDto;
import io.mosip.kernel.masterdata.entity.RegistrationCenterMachineDevice;
import io.mosip.kernel.masterdata.entity.RegistrationCenterMachineDeviceHistory;
import io.mosip.kernel.masterdata.entity.id.RegistrationCenterMachineDeviceID;
import io.mosip.kernel.masterdata.exception.MasterDataServiceException;
import io.mosip.kernel.masterdata.repository.RegistrationCenterMachineDeviceHistoryRepository;
import io.mosip.kernel.masterdata.repository.RegistrationCenterMachineDeviceRepository;
import io.mosip.kernel.masterdata.service.RegistrationCenterMachineDeviceService;
import io.mosip.kernel.masterdata.utils.ExceptionUtils;
import io.mosip.kernel.masterdata.utils.MapperUtils;
import io.mosip.kernel.masterdata.utils.MetaDataUtils;

/**
 * 
 * @author Bal Vikash Sharma
 * @since 1.0.0
 */
@Service
public class RegistrationCenterMachineDeviceServiceImpl implements RegistrationCenterMachineDeviceService {

	@Autowired
	private RegistrationCenterMachineDeviceRepository registrationCenterMachineDeviceRepository;

	@Autowired
	private RegistrationCenterMachineDeviceHistoryRepository registrationCenterMachineDeviceHistoryRepository;


	/* (non-Javadoc)
	 * @see io.mosip.kernel.masterdata.service.RegistrationCenterMachineDeviceService#createRegistrationCenterMachineAndDevice(io.mosip.kernel.masterdata.dto.RequestDto)
	 */
	@Override
	public ResponseRrgistrationCenterMachineDeviceDto createRegistrationCenterMachineAndDevice(
			RequestDto<RegistrationCenterMachineDeviceDto> requestDto) {
		ResponseRrgistrationCenterMachineDeviceDto responseRrgistrationCenterMachineDeviceDto = null;

		try {
			RegistrationCenterMachineDevice registrationCenterMachineDevice = MetaDataUtils
					.setCreateMetaData(requestDto.getRequest(), RegistrationCenterMachineDevice.class);

			RegistrationCenterMachineDevice savedRegistrationCenterMachineDevice = registrationCenterMachineDeviceRepository
					.create(registrationCenterMachineDevice);

			RegistrationCenterMachineDeviceHistory registrationCenterMachineDeviceHistory = MetaDataUtils
					.setCreateMetaData(requestDto.getRequest(), RegistrationCenterMachineDeviceHistory.class);
			registrationCenterMachineDeviceHistory
					.setEffectivetimes(savedRegistrationCenterMachineDevice.getCreatedDateTime());
			registrationCenterMachineDeviceHistoryRepository.create(registrationCenterMachineDeviceHistory);

			responseRrgistrationCenterMachineDeviceDto = MapperUtils.map(
					savedRegistrationCenterMachineDevice.getRegistrationCenterMachineDevicePk(),
					ResponseRrgistrationCenterMachineDeviceDto.class);
		} catch (DataAccessLayerException  | DataAccessException   e) {
			throw new MasterDataServiceException(
					RegistrationCenterMachineDeviceErrorCode.REGISTRATION_CENTER_MACHINE_DEVICE_CREATE_EXCEPTION
							.getErrorCode(),
					RegistrationCenterMachineDeviceErrorCode.REGISTRATION_CENTER_MACHINE_DEVICE_CREATE_EXCEPTION
							.getErrorMessage() + " " + ExceptionUtils.parseException(e));
		}

		return responseRrgistrationCenterMachineDeviceDto;
	}

    /*
     * (non-Javadoc)
     * @see io.mosip.kernel.masterdata.service.RegistrationCenterMachineDeviceService#deleteRegistrationCenterMachineAndDevice(java.lang.String, java.lang.String, java.lang.String)
     */
	@Override
	public RegistrationCenterMachineDeviceID deleteRegistrationCenterMachineAndDevice(String regCenterId,
			String machineId, String deviceId) {
		
		return null;
	}

}
