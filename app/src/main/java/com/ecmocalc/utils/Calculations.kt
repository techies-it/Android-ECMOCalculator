package com.ecmocalc.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.pow

class Calculations {

    companion object {

        /**
         * @param pounds - Lb
         * @return Kilograms - Kg
         */
        fun calPoundsToKilograms(pounds: Double): String {
            val resultKg = pounds / 2.2F
            val r = Math.round(resultKg)
            return Math.round(resultKg).toString() + " kg"
        }

        /**
         * @param kilograms - Kg
         * @return Pounds - Lb
         */
        fun calKilogramsToPounds(kilograms: Double): String {
            val resultPounds = kilograms * 2.2F
            return Math.round(resultPounds).toString() + " lbs"
        }

        /**
         * @param inches - In
         * @return Centimeters - cm
         */
        fun calInchesToCentimeters(inches: Double): String {
            val resultCentimeters = inches * 2.54F
            return String.format(Locale.ROOT, "%.2f", resultCentimeters) + " cm"
        }

        /**
         * @param centimeters - cm
         * @return Inches - In
         */
        fun calCentimetersToInches(centimeters: Double): String {
            val resultInches = centimeters / 2.54F
            return String.format(Locale.ROOT, "%.2f", resultInches) + " in"
        }

        /**
         * Calculates the body surface area (BSA) using the formula:
         * 0.007184 * Height^0.725 * Weight^0.425 and returns the result as a string with 2 decimal places.
         * @param weight - Kg
         * @param height - cm
         * @return BSA - m^2 or m²
         */
        fun calBodySurfaceArea(weight: Double, height: Double): String {
            val heightPower = height.pow(0.725)
            val weightPower = weight.pow(0.425)
            val resultBSA = 0.007184 * heightPower * weightPower
            return String.format(Locale.ROOT, "%.3f", resultBSA) + " m²"
        }

        /**
         * Calculate the Weight based Body Surface Area using the formula:
         * ((Weight * 4) + 7) / (90 + Weight )
         * @param weight - Kg
         * @return BSA - m^2 or m²
         */
        fun calWeightBasedBodySurfaceArea(weight: Double): String {
            val resultBSA = ((weight * 4) + 7) / (90 + weight)
            return String.format(Locale.ROOT, "%.2f", resultBSA) + " m²"
        }

        /**
         * Calculates the Oxygen Index (OI) using the formula:
         * OI = (FiO2 * MAP / PaO2) * 100 or (Oxygen % * Mean Airway Pressure) ÷ Partial Pressure of Arterial Oxygen
         *
         * @param fio2 the fraction of inspired oxygen (as a decimal, e.g., 0.21 for 21%) or Oxygen Percentage
         * @param map the mean airway pressure (in cmH₂O)
         * @param pao2 the partial pressure of arterial oxygen (in mmHg)
         * @return the calculated OI as a string formatted to 2 decimal places
         */
        fun calOxygenIndex(fio2: Double, map: Double, pao2: Double): String {
            val resultOxygenIndex =
                ((fio2 / 100) * map / pao2) * 100 /* Acc. to doc val resultOxygenIndex = fio2 * map / pao2 */
            return String.format(Locale.ROOT, "%.1f", resultOxygenIndex)
        }

        /**
         * Calculates the PaO2 / FiO2 Ratio using the formula:
         * (Partial Pressure of arterial Oxygen  / Oxygen % ) x 100
         *
         * @param pao2 the partial pressure of arterial oxygen (in mmHg)
         * @param fio2 the fraction of inspired oxygen (as a decimal, e.g., 0.21 for 21% oxygen)
         * @return the calculated P/F ratio
         */
        fun calPaO2ByFiO2Ratio(pao2: Double, fio2: Double): String {
            val resultPaO2ByFiO2Ratio =
                (pao2 / (fio2 / 100)) /* Acc. to doc val resultPaO2ByFiO2Ratio = (pao2/fio2)*100 */
            return Math.round(resultPaO2ByFiO2Ratio).toString()
        }

        /**
         * Calculates Heparin Loading Dose using the formula:
         * 25iu Heparin/Kg = 25 x Weight [Kg] __________ iu Heparin
         * 50iu Heparin/Kg = 50 x Weight [Kg] __________ iu Heparin
         * 75iu Heparin/Kg = 75 x Weight [Kg] __________ iu Heparin
         * 100iu Heparin/Kg = 100 x Weight [Kg] __________ iu Heparin
         * 200iu Heparin/Kg = 200 x Weight [Kg] __________ iu Heparin
         * 300iu Heparin/Kg = 300 x Weight [Kg] __________ iu Heparin
         * 400iu Heparin/Kg = 400 x Weight [Kg] __________ iu Heparin
         *
         * @param weight - Kg
         */
        fun calHeparinLoadingDose(weight: Double): String {
            return "25u/Kg = " + formatNumberWithDecimal(25 * weight) + " units\n" +
                    "50u/Kg = " + formatNumberWithDecimal(50 * weight) + " units\n" +
                    "75u/Kg = " + formatNumberWithDecimal(75 * weight) + " units\n" +
                    "100u/Kg = " + formatNumberWithDecimal(100 * weight) + " units\n" +
                    "200u/Kg = " + formatNumberWithDecimal(200 * weight) + " units\n" +
                    "300u/Kg = " + formatNumberWithDecimal(300 * weight) + " units\n" +
                    "400u/Kg = " + formatNumberWithDecimal(400 * weight) + " units"
        }

        /**
         * Calculates Cardiac Index Calculator using the formula:
         * CI [L/min/m2] 1.0 = (Body Surface Area (BSA) [m2] x 1) ___________ L/min
         * CI [L/min/m2] 1.5 = (Body Surface Area (BSA) [m2] x 1.5) ___________ L/min
         * CI [L/min/m2] 1.8 = (Body Surface Area (BSA) [m2] x 1.8) ___________ L/min
         * CI [L/min/m2] 2.0 = (Body Surface Area (BSA) [m2] x 2) ___________ L/min
         * CI [L/min/m2] 2.2 = (Body Surface Area (BSA) [m2] x 2.2) ___________ L/min
         * CI [L/min/m2] 2.4 = (Body Surface Area (BSA) [m2] x 2.4) ___________ L/min
         * CI [L/min/m2] 2.6 = (Body Surface Area (BSA) [m2] x 2.6) ___________ L/min
         * CI [L/min/m2] 2.8 = (Body Surface Area (BSA) [m2] x 2.8) ___________ L/min
         * CI [L/min/m2] 3.0 = (Body Surface Area (BSA) [m2] x 3) ___________ L/min
         *
         * @param BSA - m^2 or m²
         */
        fun calCardiacIndexCalculator(BSA: Double): String {
            return "CI 1.0 = " + String.format(Locale.ROOT, "%.2f", 1.0 * BSA) + " L/min\n" +
                    "CI 1.5 = " + String.format(Locale.ROOT, "%.2f", 1.5 * BSA) + " L/min\n" +
                    "CI 1.8 = " + String.format(Locale.ROOT, "%.2f", 1.8 * BSA) + " L/min\n" +
                    "CI 2.0 = " + String.format(Locale.ROOT, "%.2f", 2.0 * BSA) + " L/min\n" +
                    "CI 2.2 = " + String.format(Locale.ROOT, "%.2f", 2.2 * BSA) + " L/min\n" +
                    "CI 2.4 = " + String.format(Locale.ROOT, "%.2f", 2.4 * BSA) + " L/min\n" +
                    "CI 2.6 = " + String.format(Locale.ROOT, "%.2f", 2.6 * BSA) + " L/min\n" +
                    "CI 2.8 = " + String.format(Locale.ROOT, "%.2f", 2.8 * BSA) + " L/min\n" +
                    "CI 3.0 = " + String.format(Locale.ROOT, "%.2f", 3.0 * BSA) + " L/min"
        }

        /**
         * Calculates the Estimated Red Cell Mass (ERCM) using the formula:
         *  * ERCM = Weight * 75ml/Kg * (Hematocrit / 100)
         *
         * @param weight the body weight of the person (in kilograms)
         * @param hematocrit the percentage of red blood cells in the blood volume (e.g., 45 for 45%)
         * @return the calculated ERCM as a string formatted
         */
        fun calEstimatedRedCellMass(weight: Double, hematocrit: Double): String {
            val resultERCM = (weight * 75 * (hematocrit / 100))
            return Math.round(resultERCM).toString() + " ml"
        }

        /**
         * Calculates the Dilutional HCT using the formula:
         * ((Weight  x 75ml) x HCT(%)  / Circuit Volume  + (Weight  x 75ml)) x 100
         *
         * @param weight the body weight of the person (in kilograms)
         * @param hematocrit the percentage of red blood cells in the blood volume - HCT (e.g., 45 for 45%)
         * @param eclsCircuit - ECLSCircuit (Circuit Volume - mL)
         * @return the calculated Dilutional HCT as a string formatted
         */
        fun calDilutionalHCT(weight: Double, hematocrit: Double, eclsCircuit: Double): String {
            val resultDilutionalHCT =
                (((weight * 75) * (hematocrit / 100)) / (eclsCircuit + (weight * 75))) * 100
            return formatNumberWithDecimal(resultDilutionalHCT) + " %"
        }

        /**
         * Calculates the Cardiac Output (CO) using the formula:
         * (Heart Rate  x Stroke Volume ) / 1000
         *
         * @param heartRate the heart rate value (B/min)
         * @param strokeVolume the stroke volume value (mL)
         * @return the calculated Cardiac Output as a string formatted (L/min)
         */
        fun calCardiacOutput(heartRate: Double, strokeVolume: Double): String {
            val resultCardiacOutput = (heartRate * strokeVolume) / 1000.0
            return String.format(Locale.ROOT, "%.2f", resultCardiacOutput) + " L/min"
        }

        /**
         * Calculates the Cardiac Index (CI) using the formula:
         * Cardiac Output / Body Surface Area
         *
         * @param cardiacOutput - Cardiac Output value - (L/min)
         * @param bsa - Body Surface Area (BSA) - m²
         * @return the calculated Cardiac Index as a string formatted (L/min/m2)
         */
        fun calCardiacIndex(cardiacOutput: Double, bsa: Double): String {
            val resultCardiacIndex = cardiacOutput / bsa
            return String.format(Locale.ROOT, "%.1f", resultCardiacIndex) + " L/min/m²"
        }

        /**
         * Calculates the Systemic Vascular Resistance (SVR) using the formula:
         * ((MAP - CVP) / CO) x 80
         *
         * @param map - Mean Arterial Pressure (MAP) - (in mmHg)
         * @param cvp - Central Venous Pressure (CVP ) - (in mmHg)
         * @param co - Cardiac Output (in L/min)
         * @return the calculated Pulmonary Vascular Resistance (PVR) as a string formatted
         */
        fun calSystemicVascularResistance(map: Double, cvp: Double, co: Double): String {
            val resultSVR = ((map - cvp) / co) * 80
            return Math.round(resultSVR).toString() + " Dynes-sec/cm⁵"
        }

        /**
         * Calculates the Pulmonary Vascular Resistance (PVR) using the formula:
         * ((Mean Pulmonary Artery Pressure (MPAP)  - Pulmonary Capillary Wedge Pressure (PCWP)  / Cardiac Output (CO)  x 80
         *
         * @param mpap - Mean Pulmonary Artery Pressure (in mmHg)
         * @param pcwp - Pulmonary Capillary Wedge Pressure (in mmHg)
         * @param co - Cardiac Output (in L/min)
         * @return the calculated Pulmonary Vascular Resistance (PVR) as a string formatted
         */
        fun calPulmonaryVascularResistance(mpap: Double, pcwp: Double, co: Double): String {
            val resultPVR = ((mpap - pcwp) / co) * 80
            return Math.round(resultPVR).toString() + " Dynes-sec/cm⁵"
        }

        /**
         * Calculates the Oxygen Content (CaO2) - Arterial using the formula:
         * (Hemoglobin * 1.34 * Arterial Oxygen Saturation ) + (Partial Pressure of Oxygen * 0.003)
         *
         * @param hemoglobin - Hemoglobin (Hgb) - (in g/dL)
         * @param arterialOxygenSaturation - Arterial Oxygen Saturation (SaO2) - (in %)
         * @param partialPressureOfOxygen - Partial Pressure of Oxygen (PaO2) - (in mmHg)
         * @return the calculated Oxygen Content (CaO2) - Arterial as a string formatted
         */
        fun calOxygenContentArterial(
            hemoglobin: Double,
            arterialOxygenSaturation: Double,
            partialPressureOfOxygen: Double
        ): String {
            val resultCaO2 =
                (hemoglobin * 1.34 * (arterialOxygenSaturation / 100)) + (partialPressureOfOxygen * 0.003)
            return Math.round(resultCaO2).toString() + " mL/dL"
        }

        /**
         * Calculates the Oxygen Delivery (DO2) using the formula:
         * (Cardiac Output * CaO2) * 10
         *
         * @param cardiacOutput - Cardiac Output (CO) - (in L/min)
         * @param oxygenContentArterial - Oxygen Content (CaO2) - Arterial - (in mL/dL)
         * @return the calculated Oxygen Delivery (DO2) as a string formatted
         */
        fun calOxygenDelivery(
            cardiacOutput: Double,
            oxygenContentArterial: Double
        ): String {
            val resultDO2 = (cardiacOutput * oxygenContentArterial) * 10
            return Math.round(resultDO2).toString() + " mL/min"
        }

        /**
         * Calculates the Oxygen Content (CvO2) - Venous using the formula:
         * (Cardiac Output * CaO2) * 10
         *
         * @param hemoglobin - Hemoglobin (Hgb) - (in g/dL)
         * @param venousOxygenSaturation - Venous Oxygen Saturation (SvO2) - (in %)
         * @param partialPressureOfOxygen - Partial Pressure of Oxygen (PvO2) - (in mmHg)
         * @return the calculated Oxygen Content (CvO2) - Venous as a string formatted
         */
        fun calOxygenContentVenous(
            hemoglobin: Double,
            venousOxygenSaturation: Double,
            partialPressureOfOxygen: Double
        ): String {
            val resultCvO2 =
                (hemoglobin * 1.34 * (venousOxygenSaturation / 100)) + (partialPressureOfOxygen * 0.003)
            return String.format(Locale.ROOT, "%.2f", resultCvO2) + " mL/dL"
        }

        /**
         * Calculates the Oxygen Consumption (VO2) using the formula:
         * (Cardiac Output  * (CaO2-CvO2)) * 10
         *
         * @param cardiacOutput - Cardiac Output (CO) - (in L/min)
         * @param oxygenContentArterialVenous - Oxygen Content (CaO2)- Arterial - Oxygen Content Venous (CvO2) - (in CaO2 - CvO2)
         * @return the calculated Oxygen Consumption (VO2) as a string formatted
         */
        fun calOxygenConsumption(
            cardiacOutput: Double,
            oxygenContentArterialVenous: Double
        ): String {
            val resultVO2 =
                (cardiacOutput * oxygenContentArterialVenous) * 10
            return Math.round(resultVO2).toString() + " mL/min"
        }

        /**
         * Calculates the Oxygen Consumption (VO2) using the formula:
         * (Cardiac Output  * (CaO2 - CvO2)) * 10
         *
         * @param cardiacOutput - Cardiac Output (CO) - (in L/min)
         * @param oxygenContentArterial - Oxygen Content (CaO2)- Arterial - (in mL/dL)
         * @param oxygenContentVenous - Oxygen Content Venous (CvO2) - (in mL/dL)
         * @return the calculated Oxygen Consumption (VO2) as a string formatted
         */
        fun calOxygenConsumption2(
            cardiacOutput: Double,
            oxygenContentArterial: Double,
            oxygenContentVenous: Double
        ): String {
            val resultVO2 =
                (cardiacOutput * (oxygenContentArterial - oxygenContentVenous)) * 10
            return Math.round(resultVO2).toString() + " mL/min"
        }

        /**
         * Calculates the Sweep Gas using the formula:
         * (Current Patient Arterial CO2 * Current Sweep Gas Flow Rate ) ÷ Desired Patient Arterial CO2
         *
         * @param currentPatientArterial - Current Patient Arterial CO2 (PaCO2) - (in mmHg)
         * @param currentSweepGasFlowRate - Current Sweep Gas Flow Rate - (in L/min)
         * @param desiredPatientArterial - Desired Patient Arterial CO2 (PaCO2)
         * @return the calculated Sweep Gas as a string formatted (L/min)
         */
        fun calSweepGas(
            currentPatientArterial: Double,
            currentSweepGasFlowRate: Double,
            desiredPatientArterial: Double
        ): String {
            val resultSweepGas =
                (currentPatientArterial * currentSweepGasFlowRate) / desiredPatientArterial
            return String.format(Locale.ROOT, "%.1f", resultSweepGas) + " L/min"
        }

        /**
         * Calculates the Target Blood Flow For Pediatric Entry using the formula:
         * (weight x Target C.I.) / 1000
         *
         * @param weight - Current Patient Weight - (in Kg)
         * @param targetCI - Cardiac Index value - (L/min/m²)
         * @return the calculated Target Blood Flow For Pediatric Entry as a string formatted (L/min)
         */
        fun calTargetBloodFlowForPediatricEntry(weight: Double, targetCI: Double): String {
            val resultTargetBloodFlow = (weight * targetCI) / 1000
            return String.format(Locale.ROOT, "%.2f", resultTargetBloodFlow) + " L/min"
        }

        /**
         * Calculates the Target Blood Flow For Pediatric Entry using the formula:
         * CardiacOutput / weight
         *
         * @param cardiacOutput - Cardiac Output value - (in L/min)
         * @param weight - Weight value - (in Kg)
         * @return the calculated Target Blood Flow For Adult Entry as a string formatted (mL/Kg/min)
         */
        fun calTargetBloodFlowForAdultEntry(cardiacOutput: Double, weight: Double): String {
            val resultTargetBloodFlow = (cardiacOutput * 1000) / weight
            return String.format(Locale.ROOT, "%.2f", resultTargetBloodFlow) + " mL/Kg/min"
        }

        /**
         * Calculates the Cardiac Output (CO) using the formula:
         * (Cardiac Index  x BSA )
         *
         * @param cardiacIndex - Cardiac Index value - (L/min/m²)
         * @param bsa - Body Surface Area (BSA) - m²
         * @return the calculated Cardiac Output (CO) as a string formatted (L/min)
         */
        fun calCardiacOutputWithCIAndBSA(cardiacIndex: Double, bsa: Double): String {
            val resultCardiacIndex = cardiacIndex * bsa
            return String.format(Locale.ROOT, "%.2f", resultCardiacIndex) + " L/min"
        }

        /**
         * Case1 input = 10999.19 output = 10,999.2
         * Case2 input = 10999.14 output = 10,999.1
         */
        private fun formatNumberWithDecimal(number: Double): String {
            val decimalFormat = if (number % 1 == 0.0) {
                DecimalFormat("#,###")
            } else {
                DecimalFormat("#,###.0").apply {
                    roundingMode = RoundingMode.HALF_UP
                }
            }
            return decimalFormat.format(number)
        }

        /**
         * Case1 input = 10999.19 output = 10,999.19
         * Case2 input = 10999.14 output = 10,999.14
         */
        private fun formatNumber(number: Double): String {
            val numberFormat = NumberFormat.getNumberInstance(Locale.ROOT)
            numberFormat.maximumFractionDigits = 1
            numberFormat.minimumFractionDigits = 1
            return numberFormat.format(number)
        }


    }
}

// https://stackoverflow.com/questions/49011924/round-double-to-1-decimal-place-kotlin-from-0-044999-to-0-1
// ⁰ ¹ ² ³ ⁴ ⁵ ⁶ ⁷ ⁸ ⁹ ⁺ ⁻ ⁼ ⁽ ⁾ ₀ ₁ ₂ ₃ ₄ ₅ ₆ ₇ ₈ ₉ ₊ ₋ ₌ ₍ ₎