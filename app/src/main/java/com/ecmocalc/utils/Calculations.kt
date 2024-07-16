package com.ecmocalc.utils

import com.ecmocalc.models.StaticValues
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
            return String.format(Locale.ROOT, "%.2f", resultKg) + " kg"
        }

        /**
         * @param kilograms - Kg
         * @return Pounds - Lb
         */
        fun calKilogramsToPounds(kilograms: Double): String {
            val resultPounds = kilograms * 2.2F
            return String.format(Locale.ROOT, "%.2f", resultPounds) + " lbs"
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
            return String.format(Locale.ROOT, "%.2f", resultBSA) + " m²"
        }

        fun calBodySurfaceArea1(weight: Double, height: Double): String {
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
                ((map * (fio2 / 100)) / pao2) * 100 /* Acc. to doc val resultOxygenIndex = fio2(%) * map / pao2 */
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
        fun calHeparinLoadingDose(weight: Double): ArrayList<StaticValues> {
            val resultHeparinLoadingDoseList: ArrayList<StaticValues> = ArrayList<StaticValues>()
            resultHeparinLoadingDoseList.clear()
            resultHeparinLoadingDoseList.add(StaticValues("25u/Kg = " + formatNumberWithDecimal(25 * weight) + " units"))
            resultHeparinLoadingDoseList.add(StaticValues("50u/Kg = " + formatNumberWithDecimal(50 * weight) + " units"))
            resultHeparinLoadingDoseList.add(StaticValues("75u/Kg = " + formatNumberWithDecimal(75 * weight) + " units"))
            resultHeparinLoadingDoseList.add(StaticValues("100u/Kg = " + formatNumberWithDecimal(100 * weight) + " units"))
            resultHeparinLoadingDoseList.add(StaticValues("200u/Kg = " + formatNumberWithDecimal(200 * weight) + " units"))
            resultHeparinLoadingDoseList.add(StaticValues("300u/Kg = " + formatNumberWithDecimal(300 * weight) + " units"))
            resultHeparinLoadingDoseList.add(StaticValues("400u/Kg = " + formatNumberWithDecimal(400 * weight) + " units"))
            return resultHeparinLoadingDoseList
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
        fun calCardiacIndexCalculator(BSA: Double): ArrayList<StaticValues> {
            val resultCardiacIndexCalculatorList: ArrayList<StaticValues> = ArrayList<StaticValues>()
            resultCardiacIndexCalculatorList.clear()
            resultCardiacIndexCalculatorList.add(StaticValues("CI 1.0 = " + String.format(Locale.ROOT, "%.2f", 1.0 * BSA) + " L/min"))
            resultCardiacIndexCalculatorList.add(StaticValues("CI 1.5 = " + String.format(Locale.ROOT, "%.2f", 1.5 * BSA) + " L/min"))
            resultCardiacIndexCalculatorList.add(StaticValues("CI 1.8 = " + String.format(Locale.ROOT, "%.2f", 1.8 * BSA) + " L/min"))
            resultCardiacIndexCalculatorList.add(StaticValues("CI 2.0 = " + String.format(Locale.ROOT, "%.2f", 2.0 * BSA) + " L/min"))
            resultCardiacIndexCalculatorList.add(StaticValues("CI 2.2 = " + String.format(Locale.ROOT, "%.2f", 2.2 * BSA) + " L/min"))
            resultCardiacIndexCalculatorList.add(StaticValues("CI 2.4 = " + String.format(Locale.ROOT, "%.2f", 2.4 * BSA) + " L/min"))
            resultCardiacIndexCalculatorList.add(StaticValues("CI 2.6 = " + String.format(Locale.ROOT, "%.2f", 2.6 * BSA) + " L/min"))
            resultCardiacIndexCalculatorList.add(StaticValues("CI 2.8 = " + String.format(Locale.ROOT, "%.2f", 2.8 * BSA) + " L/min"))
            resultCardiacIndexCalculatorList.add(StaticValues("CI 3.0 = " + String.format(Locale.ROOT, "%.2f", 3.0 * BSA) + " L/min"))
            return  resultCardiacIndexCalculatorList
        }

        /**
         * Calculates the Estimated Red Cell Mass (ERCM) using the formula:
         *  * ERCM = Weight * 75ml/Kg * (Hematocrit / 100)
         *
         * @param weight the body weight of the person (in kilograms)
         * @param hematocrit the percentage of red blood cells in the blood volume (e.g., 0.45 for 45%)
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
            return formatNumberWithDecimal1(resultDilutionalHCT) + " %"
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

        private fun formatNumberWithDecimal1(number: Double): String {
            val hasFractionalPart = number % 1 != 0.0

            val decimalFormat = DecimalFormat(if (hasFractionalPart) "#,###.#" else "#,###").apply {
                roundingMode = RoundingMode.HALF_UP
            }
            var res = String.format(Locale.ROOT, "%.1f", decimalFormat.format(number).toDouble())
            return res
        }

        private fun formatNumberWithDecimalForTwoDecimalPlaces(number: Double): String {
            val decimalFormat = if (number % 1 == 0.00) {
                DecimalFormat("#,###")
            } else {
                DecimalFormat("#,###.00").apply {
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

        /* Start : Logic for VA Neck, VA Groin, and VVDL list for pediatric */
        fun getBioMedicusNextGenPediatricArterialCannulaeForPed(targetBloodFlow:Double):StaticValues{
            return when(targetBloodFlow){
                in 0.0..0.625 -> StaticValues(name = "Bio-Medicus NextGen Pediatric Arterial Cannulae", value = "Arterial 8Fr")
                in 0.626..1.25 -> StaticValues(name = "Bio-Medicus NextGen Pediatric Arterial Cannulae", value = "Arterial 10Fr")
                in 1.251..2.062 -> StaticValues(name = "Bio-Medicus NextGen Pediatric Arterial Cannulae", value = "Arterial 12Fr")
                in 2.063..2.5 -> StaticValues(name = "Bio-Medicus NextGen Pediatric Arterial Cannulae", value = "Arterial 14Fr")
                else -> StaticValues(name = "NA", value = "NA")
            }
        }

        private fun getBioMedicusNextGenPediatricVenousCannulaeForPed(targetBloodFlow:Double):StaticValues{
            return when(targetBloodFlow){
                in 0.0..0.75 -> StaticValues(name = "Bio-Medicus NextGen Pediatric Venous Cannulae", value = "Venous 8Fr")
                in 0.751..1.375 -> StaticValues(name = "Bio-Medicus NextGen Pediatric Venous Cannulae", value = "Venous 10Fr")
                in 1.376..2.187 -> StaticValues(name = "Bio-Medicus NextGen Pediatric Venous Cannulae", value = "Venous 12Fr")
                in 2.188..2.8 -> StaticValues(name = "Bio-Medicus NextGen Pediatric Venous Cannulae", value = "Venous 14Fr")
                else -> StaticValues(name = "NA", value = "NA")
            }
        }

        private fun getAvalonEliteBiCavalDualLumenCatheterForPed(targetBloodFlow:Double):StaticValues{
            return when(targetBloodFlow){
                in 0.0..0.625 -> StaticValues(name = "Avalon Elite Bi-Caval Dual Lumen Catheter", value = "IJV 13Fr")
                in 0.626..0.9 -> StaticValues(name = "Avalon Elite Bi-Caval Dual Lumen Catheter", value = "IJV 16Fr")
                in 0.901..1.58 -> StaticValues(name = "Avalon Elite Bi-Caval Dual Lumen Catheter", value = "IJV 19Fr")
                else -> StaticValues(name = "NA", value = "NA")
            }
        }

        private fun getCrescentDualLumenVVECLSCannulaForPed(targetBloodFlow:Double):StaticValues{
            return when(targetBloodFlow){
                in 0.0..0.75 -> StaticValues(name = "Crescent Dual Lumen VV ECLS Cannula", value = "IJV 13Fr")
                in 0.751..1.25 -> StaticValues(name = "Crescent Dual Lumen VV ECLS Cannula", value = "IJV 15Fr")
                in 1.26..1.8 -> StaticValues(name = "Crescent Dual Lumen VV ECLS Cannula", value = "IJV 19Fr")
                else -> StaticValues(name = "NA", value = "NA")
            }
        }

        fun getVANeckForPed(targetBloodFlow:Double):ArrayList<StaticValues>{
            var list = ArrayList<StaticValues>()
            list.clear()
            list.add(getBioMedicusNextGenPediatricArterialCannulaeForPed(targetBloodFlow))
            list.add(getBioMedicusNextGenPediatricVenousCannulaeForPed(targetBloodFlow))
            return list
        }

        fun getVAGroinForPed(targetBloodFlow:Double):ArrayList<StaticValues>{
            var list = ArrayList<StaticValues>()
            list.clear()
            list.add(getBioMedicusNextGenPediatricArterialCannulaeForPed(targetBloodFlow))
            list.add(getBioMedicusNextGenPediatricVenousCannulaeForPed(targetBloodFlow))
            return list
        }

        fun getVVDLForPed(targetBloodFlow:Double):ArrayList<StaticValues>{
            var list = ArrayList<StaticValues>()
            list.clear()
            list.add(getAvalonEliteBiCavalDualLumenCatheterForPed(targetBloodFlow))
            list.add(getCrescentDualLumenVVECLSCannulaForPed(targetBloodFlow))
            return list
        }

        /* End : Logic for VA Neck, VA Groin, and VVDL list for pediatric */

        /* Start : Logic for VA Neck, VA Groin, and VVDL list for adult */

        private fun getBioMedicusNextGenFemoralArterialOrJugularArterialCannulaForAud(targetBloodFlow:Double):StaticValues{
            return when(targetBloodFlow){
                in 0.0..3.1 -> StaticValues(name = "Bio-Medicus NextGen Femoral Arterial or Jugular Arterial Cannula", value = "Arterial 15Fr")
                in 3.101..4.1 -> StaticValues(name = "Bio-Medicus NextGen Femoral Arterial or Jugular Arterial Cannula", value = "Arterial 17Fr")
                in 4.101..5.5 -> StaticValues(name = "Bio-Medicus NextGen Femoral Arterial or Jugular Arterial Cannula", value = "Arterial 19Fr")
                in 5.501..6.5 -> StaticValues(name = "Bio-Medicus NextGen Femoral Arterial or Jugular Arterial Cannula", value = "Arterial 21Fr")
                in 6.5..7.0 -> StaticValues(name = "Bio-Medicus NextGen Femoral Arterial or Jugular Arterial Cannula", value = "Arterial 23Fr")
                in 6.5..7.5 -> StaticValues(name = "Bio-Medicus NextGen Femoral Arterial or Jugular Arterial Cannula", value = "Arterial 25Fr")
                else -> StaticValues(name = "NA", value = "NA")
            }
        }
        private fun getBioMedicusNextGenFemoralVenousCannulaeForAud(targetBloodFlow:Double):StaticValues{
            return when(targetBloodFlow){
                in 0.0..2.3 -> StaticValues(name = "Bio-Medicus NextGen Femoral Venous Cannulae", value = "Venous 15Fr")
                in 2.301..3.2 -> StaticValues(name = "Bio-Medicus NextGen Femoral Venous Cannulae", value = "Venous 17Fr")
                in 3.201..4.35 -> StaticValues(name = "Bio-Medicus NextGen Femoral Venous Cannulae", value = "Venous 19Fr")
                in 4.351..5.7 -> StaticValues(name = "Bio-Medicus NextGen Femoral Venous Cannulae", value = "Venous 21Fr")
                in 6.0..6.5 -> StaticValues(name = "Bio-Medicus NextGen Femoral Venous Cannulae", value = "Venous 23Fr")
                in 6.0..7.0 -> StaticValues(name = "Bio-Medicus NextGen Femoral Venous Cannulae", value = "Venous 25Fr")
                in 6.0..7.25 -> StaticValues(name = "Bio-Medicus NextGen Femoral Venous Cannulae", value = "Venous 27Fr")
                in 6.0..7.5 -> StaticValues(name = "Bio-Medicus NextGen Femoral Venous Cannulae", value = "Venous 29Fr")
                else -> StaticValues(name = "NA", value = "NA")
            }
        }
        private fun getGetingeArterialHLSCannula15cmForAud(targetBloodFlow:Double):StaticValues{
            return when(targetBloodFlow){
                in 0.0..2.125 -> StaticValues(name = "Getinge Arterial HLS Cannula (15cm)", value = "Arterial 13Fr")
                in 2.126..2.8 -> StaticValues(name = "Getinge Arterial HLS Cannula (15cm)", value = "Arterial 15Fr")
                in 2.81..4.0 -> StaticValues(name = "Getinge Arterial HLS Cannula (15cm)", value = "Arterial 17Fr")
                in 4.01..5.125 -> StaticValues(name = "Getinge Arterial HLS Cannula (15cm)", value = "Arterial 19Fr")
                in 5.126..6.375 -> StaticValues(name = "Getinge Arterial HLS Cannula (15cm)", value = "Arterial 21Fr")
                in 6.376..7.0 -> StaticValues(name = "Getinge Arterial HLS Cannula (15cm)", value = "Arterial 23Fr")
                else -> StaticValues(name = "NA", value = "NA")
            }
        }
        private fun getGetingeVenousHLSCannula15cmForAud(targetBloodFlow:Double):StaticValues{
            return when(targetBloodFlow){
                in 0.0..2.125 -> StaticValues(name = "Getinge Venous HLS Cannula (15cm)", value = "Venous 13Fr")
                in 2.126..2.8 -> StaticValues(name = "Getinge Venous HLS Cannula (15cm)", value = "Venous 15Fr")
                in 2.81..4.0 -> StaticValues(name = "Getinge Venous HLS Cannula (15cm)", value = "Venous 17Fr")
                in 4.01..5.125 -> StaticValues(name = "Getinge Venous HLS Cannula (15cm)", value = "Venous 19Fr")
                in 5.126..6.375 -> StaticValues(name = "Getinge Venous HLS Cannula (15cm)", value = "Venous 21Fr")
                in 6.376..7.0 -> StaticValues(name = "Getinge Venous HLS Cannula (15cm)", value = "Venous 23Fr")
                else -> StaticValues(name = "NA", value = "NA")
            }
        }
        private fun getGetingeArterialHLSCannula23cmForAud(targetBloodFlow:Double):StaticValues{
            return when(targetBloodFlow){
                in 0.0..2.625 -> StaticValues(name = "Getinge Arterial HLS Cannula (23cm)", value = "Arterial 15Fr")
                in 2.626..3.625 -> StaticValues(name = "Getinge Arterial HLS Cannula (23cm)", value = "Arterial 17Fr")
                in 3.626..4.6 -> StaticValues(name = "Getinge Arterial HLS Cannula (23cm)", value = "Arterial 19Fr")
                in 4.601..5.875 -> StaticValues(name = "Getinge Arterial HLS Cannula (23cm)", value = "Arterial 21Fr")
                in 5.876..7.0 -> StaticValues(name = "Getinge Arterial HLS Cannula (23cm)", value = "Arterial 23Fr")
                else -> StaticValues(name = "NA", value = "NA")
            }
        }
        private fun getGetingeVenousHLSCannula23cmForAud(targetBloodFlow:Double):StaticValues{
            return when(targetBloodFlow){
                in 0.0..2.625 -> StaticValues(name = "Getinge Venous HLS Cannula (23cm)", value = "Venous 15Fr")
                in 2.626..3.625 -> StaticValues(name = "Getinge Venous HLS Cannula (23cm)", value = "Venous 17Fr")
                in 3.626..4.6 -> StaticValues(name = "Getinge Venous HLS Cannula (23cm)", value = "Venous 19Fr")
                in 4.601..5.875 -> StaticValues(name = "Getinge Venous HLS Cannula (23cm)", value = "Venous 21Fr")
                in 5.876..7.0 -> StaticValues(name = "Getinge Venous HLS Cannula (23cm)", value = "Venous 23Fr")
                else -> StaticValues(name = "NA", value = "NA")
            }
        }
        private fun getGetingeVenousHLSCannula38cmForAud(targetBloodFlow:Double):StaticValues{
            return when(targetBloodFlow){
                in 0.0..4.4 -> StaticValues(name = "Getinge Venous HLS Cannula (38cm)", value = "Venous 19Fr")
                in 4.401..5.75 -> StaticValues(name = "Getinge Venous HLS Cannula (38cm)", value = "Venous 21Fr")
                in 5.751..7.0 -> StaticValues(name = "Getinge Venous HLS Cannula (38cm)", value = "Venous 23Fr")
                in 7.01..7.25 -> StaticValues(name = "Getinge Venous HLS Cannula (38cm)", value = "Venous 25Fr")
                else -> StaticValues(name = "NA", value = "NA")
            }
        }
        private fun getGetingeVenousHLSCannula55cmForAud(targetBloodFlow:Double):StaticValues{
            return when(targetBloodFlow){
                in 0.0..5.3 -> StaticValues(name = "Getinge Venous HLS Cannula (55cm)", value = "Venous 21Fr")
                in 5.301..6.6 -> StaticValues(name = "Getinge Venous HLS Cannula (55cm)", value = "Venous 23Fr")
                in 6.601..7.0 -> StaticValues(name = "Getinge Venous HLS Cannula (55cm)", value = "Venous 25Fr")
                in 7.01..7.25 -> StaticValues(name = "Getinge Venous HLS Cannula (55cm)", value = "Venous 29Fr")
                else -> StaticValues(name = "NA", value = "NA")
            }
        }
        private fun getEdwardsQuickdrawVenousCannulaForAud(targetBloodFlow:Double):StaticValues{
            return when(targetBloodFlow){
                in 0.0..5.0 -> StaticValues(name = "Edwards Quickdraw Venous Cannula", value = "Venous 22Fr")
                in 5.01..6.5 -> StaticValues(name = "Edwards Quickdraw Venous Cannula", value = "Venous 25Fr")
                else -> StaticValues(name = "NA", value = "NA")
            }
        }
        private fun getBioMedicusMultiStageFemoralVenousCannulaeForAud(targetBloodFlow:Double):StaticValues{
            return when(targetBloodFlow){
                in 0.0..4.1 -> StaticValues(name = "Bio-Medicus Multi-Stage Femoral Venous Cannulae", value = "Venous 19Fr")
                in 4.101..5.375 -> StaticValues(name = "Bio-Medicus Multi-Stage Femoral Venous Cannulae", value = "Venous 21Fr")
                in 5.376..7.0 -> StaticValues(name = "Bio-Medicus Multi-Stage Femoral Venous Cannulae", value = "Venous 25Fr")
                else -> StaticValues(name = "NA", value = "NA")
            }
        }

        private fun getAvalonEliteBiCavalDualLumenCatheterForAud(targetBloodFlow:Double):StaticValues{
            return when(targetBloodFlow){
                in 0.0..1.4 -> StaticValues(name = "Avalon Elite Bi-Caval Dual Lumen Catheter", value = "IJV 20Fr")
                in 1.401..2.25 -> StaticValues(name = "Avalon Elite Bi-Caval Dual Lumen Catheter", value = "IJV 23Fr")
                in 2.251..3.35 -> StaticValues(name = "Avalon Elite Bi-Caval Dual Lumen Catheter", value = "IJV 27Fr")
                in 3.351..4.75 -> StaticValues(name = "Avalon Elite Bi-Caval Dual Lumen Catheter", value = "IJV 31Fr")
                else -> StaticValues(name = "NA", value = "NA")
            }
        }

        private fun getCrescentDualLumenVVECLSCannulaForAud(targetBloodFlow:Double):StaticValues{
            return when(targetBloodFlow){
                in 0.0..2.7 -> StaticValues(name = "Crescent Dual Lumen VV ECLS Cannula", value = "IJV 24Fr")
                in 2.701..3.35 -> StaticValues(name = "Crescent Dual Lumen VV ECLS Cannula", value = "IJV 26Fr")
                in 3.351..4.0 -> StaticValues(name = "Crescent Dual Lumen VV ECLS Cannula", value = "IJV 28Fr")
                in 4.001..4.75 -> StaticValues(name = "Crescent Dual Lumen VV ECLS Cannula", value = "IJV 30Fr")
                in 4.751..5.6 -> StaticValues(name = "Crescent Dual Lumen VV ECLS Cannula", value = "IJV 32Fr")
                else -> StaticValues(name = "NA", value = "NA")
            }
        }


        fun getVANeckForAud(targetBloodFlow:Double):ArrayList<StaticValues>{
            var list = ArrayList<StaticValues>()
            list.clear()
            list.add(getBioMedicusNextGenFemoralArterialOrJugularArterialCannulaForAud(targetBloodFlow))
            list.add(getBioMedicusNextGenFemoralVenousCannulaeForAud(targetBloodFlow))
            list.add(getGetingeArterialHLSCannula15cmForAud(targetBloodFlow))
            list.add(getGetingeArterialHLSCannula23cmForAud(targetBloodFlow))
            list.add(getGetingeVenousHLSCannula15cmForAud(targetBloodFlow))
            list.add(getGetingeVenousHLSCannula23cmForAud(targetBloodFlow))
            return list
        }

        fun getVAGroinForAud(targetBloodFlow:Double):ArrayList<StaticValues>{
            var list = ArrayList<StaticValues>()
            list.clear()
            list.add(getBioMedicusNextGenFemoralArterialOrJugularArterialCannulaForAud(targetBloodFlow))
            list.add(getBioMedicusNextGenFemoralVenousCannulaeForAud(targetBloodFlow))
            list.add(getBioMedicusMultiStageFemoralVenousCannulaeForAud(targetBloodFlow))
            list.add(getGetingeArterialHLSCannula15cmForAud(targetBloodFlow))
            list.add(getGetingeArterialHLSCannula23cmForAud(targetBloodFlow))
            list.add(getGetingeVenousHLSCannula23cmForAud(targetBloodFlow))
            list.add(getGetingeVenousHLSCannula38cmForAud(targetBloodFlow))
            list.add(getGetingeVenousHLSCannula55cmForAud(targetBloodFlow))
            list.add(getEdwardsQuickdrawVenousCannulaForAud(targetBloodFlow))
            return list
        }

        fun getVVDLForAud(targetBloodFlow:Double):ArrayList<StaticValues>{
            var list = ArrayList<StaticValues>()
            list.clear()
            list.add(getAvalonEliteBiCavalDualLumenCatheterForAud(targetBloodFlow))
            list.add(getCrescentDualLumenVVECLSCannulaForAud(targetBloodFlow))
            return list
        }



        /* End : Logic for VA Neck, VA Groin, and VVDL list for adult */

    }
}

// https://stackoverflow.com/questions/49011924/round-double-to-1-decimal-place-kotlin-from-0-044999-to-0-1
// ⁰ ¹ ² ³ ⁴ ⁵ ⁶ ⁷ ⁸ ⁹ ⁺ ⁻ ⁼ ⁽ ⁾ ₀ ₁ ₂ ₃ ₄ ₅ ₆ ₇ ₈ ₉ ₊ ₋ ₌ ₍ ₎