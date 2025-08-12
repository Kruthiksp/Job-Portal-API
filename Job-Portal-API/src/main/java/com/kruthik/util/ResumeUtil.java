package com.kruthik.util;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ResumeUtil {

	private final Cloudinary cloudinary;

	public boolean isResumeValid(MultipartFile resume) {

		long maxSize = 5 * 1024 * 1024; // 5 MB

		if (resume == null || resume.isEmpty()) {
			throw new IllegalArgumentException("Resume file is required");
		}

		if (!isPdfFile(resume)) {
			throw new IllegalArgumentException("Only PDF files are allowed");
		}

		if (resume.getSize() > maxSize) {
			throw new IllegalArgumentException("File size must not exceed 5 MB");
		}

		return true;
	}

	public String resumeURL(MultipartFile resume, String username) {
		String splitedUsername = username.split("@")[0];
		username.replace('.', '_');
		username.replace('@', '_');

		String folder = "JOB_PORTAL_API/" + username;
		String public_id = splitedUsername + "-Resume";

		try {
			Map uploadedResume = cloudinary.uploader().upload(resume.getBytes(),
					ObjectUtils.asMap("folder", folder, "public_id", public_id, "overwrite", true));
			return uploadedResume.get("secure_url").toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to upload image", e);
		}
	}

	private boolean isPdfFile(MultipartFile file) {
		try {

			/*
			 * The PDF file signature (also called magic number) is exactly 4 bytes: Creates
			 * a 4-byte array to hold the first 4 bytes of the file. (%PDF) % -> 0*25 P ->
			 * 0*50 D -> 0*44 F -> 0*46
			 */
			byte[] header = new byte[4];
			/*
			 * Gets an InputStream from the uploaded file. Reads the first 4 bytes into the
			 * header array. The 0 is the offset in the array, and 4 is the number of bytes
			 * to read.
			 */
			file.getInputStream().read(header, 0, 4);
			return header[0] == 0x25 && header[1] == 0x50 && header[2] == 0x44 && header[3] == 0x46;
		} catch (Exception e) {
			return false;
		}
	}

}
