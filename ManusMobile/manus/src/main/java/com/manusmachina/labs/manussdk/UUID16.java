/**
 * Copyright (C) 2015 Manus Machina
 *
 * This file is part of the Manus SDK.
 *
 * Manus SDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Manus SDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Manus SDK. If not, see <http://www.gnu.org/licenses/>.
 */

package com.manusmachina.labs.manussdk;

import java.util.UUID;

/**
 * Created by Armada on 14-4-2015.
 */
class UUID16 {
    private static final UUID BLE_BASE = new UUID(0x1000L, 0x800000805F9B34FBL);
    private static final UUID MANUS_BASE = new UUID(0x1BC500000200ECA1L, 0xE41120FAC04AFA8FL);

    public static UUID BLEToUUID(byte mostSigBits, byte leastSigBits) {
        long sigBits = (mostSigBits << 8) | leastSigBits;
        return new UUID(BLE_BASE.getMostSignificantBits() | (sigBits << 32), BLE_BASE.getLeastSignificantBits());
    }

    public static UUID BLEToUUID(int mostSigBits, int leastSigBits) {
        // Because Java doesn't allow you to declare literals as bytes.
        return BLEToUUID((byte) mostSigBits, (byte) leastSigBits);
    }

    public static UUID ManusToUUID(byte mostSigBits, byte leastSigBits) {
        long sigBits = (mostSigBits << 8) | leastSigBits;
        return new UUID(MANUS_BASE.getMostSignificantBits() | (sigBits << 32), MANUS_BASE.getLeastSignificantBits());
    }

    public static UUID ManusToUUID(int mostSigBits, int leastSigBits) {
        // Because Java doesn't allow you to declare literals as bytes.
        return ManusToUUID((byte) mostSigBits, (byte) leastSigBits);
    }
}
